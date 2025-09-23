package com.querydsl.entity;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberTest {
    @Autowired
    private EntityManager em;

    @Test
    public void testEntity() {
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");

        em.persist(team1);
        em.persist(team2);

        Member member1 = new Member("Member1", 10, team1);
        Member member2 = new Member("Member2", 20, team1);
        Member member3 = new Member("Member3", 30, team2);
        Member member4 = new Member("Member4", 40, team2);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);
        
        // 초기회
        em.flush();
        em.clear();

        List<Member> members = em.createQuery("SELECT m FROM Member m", Member.class)
                .getResultList();
        
        for (Member member : members) {
            System.out.println("member = " + member);
            System.out.println("member.getTeam() = " + member.getTeam());
        }
    }
}