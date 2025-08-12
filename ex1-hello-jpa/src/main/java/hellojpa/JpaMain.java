package hellojpa;

import jakarta.persistence.*;

import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            // Member 등록
//            Member member = new Member();
//            member.setId(2L);
//            member.setName("HelloB");
//
//            em.persist(member);

            // Member 단일 조회
//            Member findMember = em.find(Member.class, 1L);
//            System.out.println("findMember.getId() = " + findMember.getId());
//            System.out.println("findMember.getName() = " + findMember.getName());

            // Member 수정
//            findMember.setName("HelloA");

            // Member 삭제
//            em.remove(findMember);

            // Member 목록 조회
//            List<Member> members = em.createQuery("SELECT m FROM Member AS m", Member.class)
//                    .setFirstResult(0)
//                    .setMaxResults(10)
//                    .getResultList();
//
//            for (Member member : members) {
//                System.out.println("member.getName() = " + member.getName());
//            }

            // 비영속
//            Member member = new Member();
//            member.setId(1L);
//            member.setName("HelloA");

            // 영속
//            System.out.println("----- BEFORE -----");
//            em.persist(member);
//            System.out.println("----- AFTER -----");

//            Member findMember = em.find(Member.class, 3L);
//            System.out.println("findMember.getId() = " + findMember.getId());
//            System.out.println("findMember.getName() = " + findMember.getName());
//
//            Member findMember2 = em.find(Member.class, 1L);
//            Member findMember3 = em.find(Member.class, 1L);
//
//            System.out.println("result = " + (findMember2 == findMember3));

//            Member member1 = new Member(4L, "HelloD");
//            Member member2 = new Member(5L, "HelloF");
//
//            em.persist(member1);
//            em.persist(member2);

//            Member member = em.find(Member.class, 6L);
//            member.setName("Hello6");
//
//            System.out.println("----- Line -----");

            // 플러쉬
//            Member member = new Member(6L, "hello6");
//            em.persist(member);
//
//            em.flush();
//
//            System.out.println("----- Line -----");

            // 준영속
            Member member = em.find(Member.class, 6L);
            member.setName("HelloF");

            em.detach(member);
            em.clear();
            em.close();

            Member member2 = em.find(Member.class, 6L);

            tx.commit();
        }
        catch (Exception e) {
            tx.rollback();
        }
        finally {
            em.close();
        }

        emf.close();
    }
}