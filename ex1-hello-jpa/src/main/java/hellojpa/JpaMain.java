package hellojpa;

import jakarta.persistence.*;

import java.time.LocalDateTime;
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

            // 플러쉬 - 영속성의 변경 내용을 데이터베이스에 반영
//            Member member = new Member(6L, "hello6");
//            em.persist(member);
//
//            em.flush();
//
//            System.out.println("----- Line -----");

            // 준영속
//            Member member = em.find(Member.class, 6L);
//            member.setName("HelloF");
//
//            em.detach(member);
//            em.clear();
//            em.close();
//
//            Member member2 = em.find(Member.class, 6L);

            // 자동 커밋 시점 확인
//            Member member = new Member();
//            member.setName("MemberB");
//            member.setRoleType(RoleType.USER);
//
//            Member member2 = new Member();
//            member2.setName("MemberC");
//            member2.setRoleType(RoleType.ADMIN);
//
//            System.out.println("---------------------");
//            em.persist(member);
//            em.persist(member2);
//            System.out.println("member.getId() = " + member.getId());
//            System.out.println("member2.getId() = " + member2.getId());
//            System.out.println("---------------------");

//            Team team = new Team();
//            team.setName("Team1");
//            em.persist(team);
//
//            Member member = new Member();
//            member.setName("Member1");
//            member.setTeam(team);
//            em.persist(member);
//
//            em.flush();
//            em.clear();

//            Member findMember = em.find(Member.class, 102L);
//
//            Team findTeam = findMember.getTeam();
//            System.out.println("findTeam = " + findTeam.getName());

            // Member에서 Team 수정
//            Team newTeam = em.find(Team.class, 2L);
//            findMember.setTeam(newTeam);

//            List<Member> members = findMember.getTeam().getMembers();
//
//            for (Member member : members) {
//                System.out.println("member = " + member.getId());
//            }

//            Movie movie = new Movie();
//            movie.setDirector("aaa");
//            movie.setActor("bbb");
//            movie.setName("ccc");
//            movie.setPrice(10000);
//
//            em.persist(movie);
//
//            em.flush();
//            em.clear();
//
//            Movie findMovie = em.find(Movie.class, movie.getId());
//            System.out.println("findMovie = " + findMovie);
//
//            // 추상 테이블 조회
//            Item findItem = em.find(Item.class, findMovie.getId());
//            System.out.println("findItem = " + findItem);

            // @MappedSuperclass
            Member member = new Member();
            member.setName("user");
            member.setCreatedBy("admin");
            member.setCreatedDate(LocalDateTime.now());

            em.persist(member);

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