package com.jpadata.datajpa.repository;

import com.jpadata.datajpa.dto.MemberDto;
import com.jpadata.datajpa.entity.Member;
import com.jpadata.datajpa.entity.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TeamRepository teamRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void testMember() {
        Member member = new Member("User1");

        Member savedMember = memberRepository.save(member);

        Member findMember = memberRepository.findById(savedMember.getId()).get();

        assertEquals(findMember.getId(), member.getId());
        assertEquals(findMember.getUsername(), member.getUsername());
        assertEquals(findMember, member);
    }

    @Test
    public void basicCRUD() {
        Member member1 = new Member("User1");
        Member member2 = new Member("User2");

        memberRepository.save(member1);
        memberRepository.save(member2);

        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();

        // 단일 조회 검증
        assertEquals(findMember1.getId(), member1.getId());
        assertEquals(findMember2.getId(), member2.getId());

        // 리스트 조회 검증
        List<Member> all = memberRepository.findAll();
        assertEquals(2, all.size());

        // 수량 검증
        long count = memberRepository.count();
        assertEquals(2, count);

        memberRepository.delete(member1);
        memberRepository.delete(member2);

        // 삭제 검증
        long deleteCount = memberRepository.count();
        assertEquals(0, deleteCount);
    }

    @Test
    public void findByUsernameAndAgeGreaterThan() {
        Member member1 = new Member("User", 10);
        Member member2 = new Member("User", 20);

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("User", 15);

        assertEquals("User", result.get(0).getUsername());
        assertEquals(20, result.get(0).getAge());

        assertEquals(1, result.size());
    }

    @Test
    public void testNamedQuery() {
        Member member1 = new Member("User1", 10);
        Member member2 = new Member("User2", 20);

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findByUsername("User1");
        assertEquals("User1", result.get(0).getUsername());
        assertEquals(10, result.get(0).getAge());
    }

    @Test
    public void testQuery() {
        Member member1 = new Member("User1", 10);
        Member member2 = new Member("User2", 20);

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findUser("User1", 10);
        assertEquals("User1", result.get(0).getUsername());
        assertEquals(10, result.get(0).getAge());

        List<String> usernameList = memberRepository.findUsernameList();

        for (String username : usernameList) {
            System.out.println("username = " + username);
        }
    }

    @Test
    public void testMemberDto() {
        Team team1 = new Team("Team1");

        teamRepository.save(team1);
        
        Member member1 = new Member("User1", 10);
        member1.setTeam(team1);

        memberRepository.save(member1);

        List<MemberDto> memberDtos = memberRepository.findMemberDto();
        
        for (MemberDto memberDto : memberDtos) {
            System.out.println("memberDto = " + memberDto);
        }
    }

    @Test
    public void findByNames() {
        Member member1 = new Member("User1", 10);
        Member member2 = new Member("User2", 20);

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> findNames = memberRepository.findByNames(Arrays.asList("User1", "User2"));

        for(Member member : findNames) {
            System.out.println("member = " + member);
        }
    }

    @Test
    public void returnType() {
        Member member1 = new Member("User1", 10);
        Member member2 = new Member("User2", 20);

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> listResult = memberRepository.findListByUsername("User1");
        Member singleResult = memberRepository.findMemberByUsername("User1");
        Optional<Member> optionalResult = memberRepository.findOptionalByUsername("User1");

        assertEquals(member1, listResult.get(0));
        assertEquals(member1, singleResult);
        assertEquals(member1, optionalResult.get());
    }

    @Test
    public void testPaging() {
        memberRepository.save(new Member("User1", 10));
        memberRepository.save(new Member("User2", 10));
        memberRepository.save(new Member("User3", 10));
        memberRepository.save(new Member("User4", 10));
        memberRepository.save(new Member("User5", 10));

        int age = 10;
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        // Page
        Page<Member> page = memberRepository.findPageByAge(age, pageRequest);

        List<Member> pageContent = page.getContent();
        long pageTotalElements = page.getTotalElements();

        assertEquals(3, pageContent.size());
        assertEquals(5, pageTotalElements);
        assertEquals(0, page.getNumber());
        assertEquals(2, page.getTotalPages());
        assertTrue(page.isFirst());
        assertTrue(page.hasNext());

        // Member -> MemberDto
        Page<MemberDto> toMap = page.map(member -> new MemberDto(member.getId(), member.getUsername(), null));

        // Slice
        Slice<Member> slice = memberRepository.findSliceByAge(age, pageRequest);

        List<Member> sliceContent = slice.getContent();

        assertEquals(3, sliceContent.size());
        assertEquals(0, slice.getNumber());
        assertTrue(slice.isFirst());
        assertTrue(slice.hasNext());
    }

    @Test
    public void testBulkUpdate() {
        memberRepository.save(new Member("User1", 10));
        memberRepository.save(new Member("User2", 15));
        memberRepository.save(new Member("User3", 20));
        memberRepository.save(new Member("User4", 25));
        memberRepository.save(new Member("User5", 30));
        memberRepository.save(new Member("User6", 35));
        memberRepository.save(new Member("User7", 40));

        int result = memberRepository.bulkAgePlus(20);

        assertEquals(5, result);

//        em.flush();
//        em.clear();

        Member findMember = memberRepository.findMemberByUsername("User5");
        System.out.println("findMember = " + findMember);
    }

    @Test
    public void findMemberLazy() {
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");

        teamRepository.save(team1);
        teamRepository.save(team2);

        memberRepository.save(new Member("User1", 10, team1));
        memberRepository.save(new Member("User2", 20, team2));

        em.flush();
        em.clear();

        List<Member> members = memberRepository.findAll();
        List<Member> fetchMembers = memberRepository.findMemberFetchJoin();

        for (Member member : members) {
            System.out.println("member.getTeam().getClass() = " + member.getTeam().getClass());
            System.out.println("fetchMembers.getClass() = " + fetchMembers.getClass());
        }
    }

    @Test
    public void testQueryHint() {
        Member member1 = new Member("User1", 10);
        memberRepository.save(member1);

        em.flush();
        em.clear();

        Member findMember = memberRepository.findReadOnlyByUsername("User1");
        findMember.setUsername("User11");

        em.flush();
    }

    @Test
    public void testQueryLock() {
        Member member1 = new Member("User1", 10);
        memberRepository.save(member1);

        em.flush();
        em.clear();

        List<Member> members = memberRepository.findLockByUsername("User1");
    }

    @Test
    public void testCustom() {
        List<Member> result = memberRepository.findMemberCustom();
    }
}