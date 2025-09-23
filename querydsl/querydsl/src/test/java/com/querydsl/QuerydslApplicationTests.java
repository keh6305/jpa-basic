package com.querydsl;

import com.querydsl.entity.Hello;
import com.querydsl.entity.QHello;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class QuerydslApplicationTests {
    @Autowired
    private EntityManager em;

    @Test
    void contextLoads() {
        Hello hello = new Hello();

        em.persist(hello);

        JPAQueryFactory query = new JPAQueryFactory(em);

//        QHello qHello = new QHello("Hello");
        QHello qHello = QHello.hello;

        Hello result = query.selectFrom(qHello)
                .fetchOne();

        assertEquals(hello, result);
        assertEquals(hello.getId(), result.getId());
    }
}