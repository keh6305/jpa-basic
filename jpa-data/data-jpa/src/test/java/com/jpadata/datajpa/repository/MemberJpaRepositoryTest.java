package com.jpadata.datajpa.repository;

import com.jpadata.datajpa.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {
    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @Test
    public void testMember() {
        Member member = new Member("User1");

        Member savedMember = memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.find(savedMember.getId());

        assertEquals(findMember.getId(), member.getId());
        assertEquals(findMember.getUsername(), member.getUsername());
        assertEquals(findMember, member);
    }

    @Test
    public void basicCRUD() {
        Member member1 = new Member("User1");
        Member member2 = new Member("User2");

        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        Member findMember1 = memberJpaRepository.find(member1.getId());
        Member findMember2 = memberJpaRepository.find(member2.getId());

        // 단일 조회 검증
        assertEquals(findMember1.getId(), member1.getId());
        assertEquals(findMember2.getId(), member2.getId());

        // 리스트 조회 검증
        List<Member> all = memberJpaRepository.findAll();
        assertEquals(2, all.size());

        // 수량 검증
        long count = memberJpaRepository.count();
        assertEquals(2, count);

        memberJpaRepository.delete(member1);
        memberJpaRepository.delete(member2);

        // 삭제 검증
        long deleteCount = memberJpaRepository.count();
        assertEquals(0, deleteCount);
    }

    @Test
    public void findByUsernameAndAgeGreaterThan() {
        Member member1 = new Member("User", 10);
        Member member2 = new Member("User", 20);

        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        List<Member> result = memberJpaRepository.findByUsernameAndAgeGreaterThan("User", 15);

        assertEquals("User", result.get(0).getUsername());
        assertEquals(20, result.get(0).getAge());

        assertEquals(1, result.size());
    }

    @Test
    public void testNamedQuery() {
        Member member1 = new Member("User1", 10);
        Member member2 = new Member("User2", 20);

        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        List<Member> result = memberJpaRepository.findByUsername("User1");

        assertEquals("User1", result.get(0).getUsername());
        assertEquals(10, result.get(0).getAge());
    }

    @Test
    public void testPaging() {
        memberJpaRepository.save(new Member("User1", 10));
        memberJpaRepository.save(new Member("User2", 10));
        memberJpaRepository.save(new Member("User3", 10));
        memberJpaRepository.save(new Member("User4", 10));
        memberJpaRepository.save(new Member("User5", 10));

        int age = 10;
        int offset = 1;
        int limit = 3;

        List<Member> members = memberJpaRepository.findByPage(age, offset, limit);
        long totalCount = memberJpaRepository.totalCount(age);

        assertEquals(3, members.size());
        assertEquals(5, totalCount);
    }
}