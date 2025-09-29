package com.querydsl.controller;

import com.querydsl.entity.Member;
import com.querydsl.entity.Team;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Profile("local")
@Component
@RequiredArgsConstructor
public class InitMember {
    private final InitMemberService initMemberService;

    @PostConstruct
    public void init() {
        initMemberService.init();
    }

    @Component
    static class InitMemberService {
        @PersistenceContext
        private EntityManager em;

        @Transactional
        public void init() {
            Team team1 = new Team("team1");
            Team team2 = new Team("team2");

            em.persist(team1);
            em.persist(team2);

            for (int i = 1; i <= 100; i++) {
                Team selectedTeam = (i % 2 == 0) ? team1 : team2;

                em.persist(new Member(("member" + i), i, selectedTeam));
            }
        }
    }
}