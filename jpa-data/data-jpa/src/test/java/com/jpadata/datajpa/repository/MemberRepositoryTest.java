package com.jpadata.datajpa.repository;

import com.jpadata.datajpa.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

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
}