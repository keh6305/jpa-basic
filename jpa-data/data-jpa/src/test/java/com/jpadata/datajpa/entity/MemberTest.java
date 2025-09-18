package com.jpadata.datajpa.entity;

import com.jpadata.datajpa.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberTest {
    @PersistenceContext
    private EntityManager em;
    
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testEntity() {
        Team team1 = new Team("team1");
        Team team2 = new Team("team2");

        em.persist(team1);
        em.persist(team2);

        Member member1 = new Member("member1", 10, team1);
        Member member2 = new Member("member2", 20, team1);
        Member member3 = new Member("member3", 30, team2);
        Member member4 = new Member("member4", 40, team2);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        // 초기화
        em.flush();
        em.clear();

        List<Member> members = em.createQuery("SELECT m FROM Member m ", Member.class)
                .getResultList();
        
        for (Member member : members) {
            System.out.println("member = " + member);
            System.out.println("member.getTeam() = " + member.getTeam());
        }
    }

    @Test
    public void testJpaBaseEntity() throws Exception {
        Member member1 = new Member("member1");
        
        memberRepository.save(member1);         // @PrePersist

        Thread.sleep(100);
        member1.setUsername("member11");
        
        em.flush();                             // @PreUpdate
        em.clear();

        Member findMember = memberRepository.findById(member1.getId()).get();

        System.out.println("findMember.getCreatedAt() = " + findMember.getCreatedAt());
        System.out.println("findMember.getUpdatedAt() = " + findMember.getUpdatedAt());
    }
}