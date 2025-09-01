package hellojpql;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
//            Member member = new Member();
//            member.setName("User1");
//            member.setAge(20);
//
//            em.persist(member);
//
//            em.flush();
//            em.clear();

            // TypedQuery 타입 조회
//            TypedQuery<Member> query1 = em.createQuery("SELECT m FROM Member m WHERE m.name = :name", Member.class);
//            query1.setParameter("name", "User1");
//
//            Member result = em.createQuery("SELECT m FROM Member m WHERE m.name = :name", Member.class)
//                    .setParameter("name", "User1")
//                    .getSingleResult();
//
//            result.setAge(25);
//
//            TypedQuery<String> query2 = em.createQuery("SELECT m.name FROM Member m",  String.class);
//
//            List<Member> resultList = query1.getResultList();
//            Member singleResult = query1.getSingleResult();
//
//            for(Member m : resultList) {
//                System.out.println("resultList = " + m.getName());
//            }
//
//            System.out.println("singleResult = " + singleResult.getName());

            // Query 타입 조회
//            Query query3 = em.createQuery("SELECT m.name, m.age FROM Member m");

            // Object 타입 조회
//            List resultList2 = em.createQuery("SELECT m.name, m.age FROM Member m")
//                    .getResultList();
//
//            Object obj = resultList2.get(0);
//            Object[] objArray = (Object[]) obj;
//
//            System.out.println("name = " + objArray[0]);
//            System.out.println("age = " + objArray[1]);

            // Object[] 타입 조회
//            List<Object[]> resultList3 = em.createQuery("SELECT m.name, m.age FROM Member m")
//                    .getResultList();
//
//            Object[] objArray2 = resultList3.get(0);

            // new 타입 조회
//            List<MemberDTO> resultList4 = em.createQuery("SELECT new hellojpql.MemberDTO(m.name, m.age) FROM Member m")
//                    .getResultList();
//
//            MemberDTO memberDTO = resultList4.get(0);
//            System.out.println("memberDTO.getName() = " + memberDTO.getName());
//            System.out.println("memberDTO.getAge() = " + memberDTO.getAge());

            Team team = new Team();
            team.setName("Team1");

            Team team2 = new Team();
            team2.setName("Team2");

            em.persist(team);
            em.persist(team2);

            for (int i = 1; i <= 10; i++) {
                Member member = new Member();
                member.setName("User" + i);
                member.setAge(10 + i);
                member.setType(MemberType.ADMIN);

                if (i <= 3) {
                    member.setTeam(team);
                } else if (i <= 7) {
                    member.setTeam(team2);
                }

                em.persist(member);
            }

            em.flush();
            em.clear();

            // 페이징
//            List<Member> result = em.createQuery("SELECT m FROM Member m ORDER BY m.age DESC",  Member.class)
//                    .setFirstResult(0)
//                    .setMaxResults(10)
//                    .getResultList();
//
//            for (Member m : result) {
//                System.out.println("m = " + m);
//            }

            // Join
//            String innerQuery = "SELECT m FROM Member m JOIN m.team t";
//            String outerQuery = "SELECT m FROM Member m LEFT JOIN m.team t";
//            String thetaQuery = "SELECT m FROM Member m, Team t WHERE t.id = m.team.id";
//
//            List<Member> result = em.createQuery(thetaQuery, Member.class)
//                    .getResultList();
//
//            for (Member m : result) {
//                System.out.println("m = " + m);
//                System.out.println("m.getTeam() = " + m.getTeam());
//            }

            // 서브쿼리
//            String query = "SELECT m FROM Member m WHERE m.age > (SELECT AVG(mm.age) FROM Member mm)";
//            List<Member> result = em.createQuery(query, Member.class).getResultList();
//
//            System.out.println("result = " + result);

            // 타입 표현
//            String query = "SELECT m.name, 'HELLO', true FROM Member m WHERE m.type = hellojpql.MemberType.ADMIN";
//            List result = em.createQuery(query).getResultList();
//
//            System.out.println("result = " + result);

            // 조건식
//            String query = "SELECT " +
//                    "CASE WHEN m.age <= 20 THEN '학생요금' " +
//                         "WHEN m.age >= 60 THEN '경로요금' " +
//                         "ELSE '일반요금' END " +
//                    "FROM Member m ";

//            String query = "SELECT COALESCE(m.name, '이름없는 회원') FROM Member m ";

//            String query = "SELECT NULLIF(m.name, 'User1') AS name FROM Member m ";
//
//            List<String> result = em.createQuery(query, String.class).getResultList();
//
//            for (String s : result) {
//                System.out.println(s);
//            }

            // JPQL 함수
//            String query = "SELECT 'a' || 'b' FROM Member m ";
//            String query = "SELECT CONCAT('a', 'b') FROM Member m ";
//            String query = "SELECT SUBSTRING(m.name, 1, 2) FROM Member m ";
//            String query = "SELECT TRIM(m.name) FROM Member m ";
//            String query = "SELECT LOCATE('er', m.name) FROM Member m ";
//
//            List<String> result = em.createQuery(query, String.class).getResultList();
//            List<Integer> result = em.createQuery(query, Integer.class).getResultList();
//
//            for (String s : result) {
//                System.out.println(s);
//            }
//
//            System.out.println("result.get(0) = " + result.get(0));

            // 경로 표현식
//            String query = "SELECT m.team FROM Member m ";
//
//            List<String> result = em.createQuery(query, String.class)
//                    .getResultList();
//
//            for (String s : result) {
//                System.out.println(s);
//            }

//            String query = "SELECT t.members FROM Team t ";
//
//            List results = em.createQuery(query, Collection.class)
//                    .getResultList();
//
//            for (Object o : results) {
//                System.out.println("o = " + o);
//            }

            // Fetch Join
//            String query = "SELECT m FROM Member m JOIN FETCH m.team ";
//
//            List<Member> result = em.createQuery(query, Member.class)
//                    .getResultList();
//
//            for (Member member : result) {
//                System.out.println("member = " + member.getName() + ", team = " + member.getTeam().getName());
//            }

//            String query = "SELECT DISTINCT t FROM Team t JOIN FETCH t.members ";
//
//            List<Team> result = em.createQuery(query, Team.class)
//                    .getResultList();
//
//            for (Team t : result) {
//                System.out.println("team = " + t.getName() + ", Member = " + t.getMembers().size());
//
//                for (Member member : t.getMembers()) {
//                    System.out.println("member = " + member);
//                }
//            }

            Member member1 = new Member();
            member1.setName("Member1");
            member1.setAge(21);
            member1.setType(MemberType.ADMIN);

            em.persist(member1);

            // 엔티티 직접 사용
//            String query = "SELECT m FROM Member m WHERE m = :member ";
//            String query = "SELECT m FROM Member m WHERE m.team = :team ";
//
//            List<Member> result = em.createQuery(query, Member.class)
//                    .setParameter("team",  team)
//                    .getResultList();
//
//            for (Member member : result) {
//                System.out.println("member = " + member.getName() + ", team = " + member.getTeam().getName());
//            }

            // named 쿼리
//            List<Member> result = em.createNamedQuery("Member.findByName", Member.class)
//                    .setParameter("name", "Member1")
//                    .getResultList();
//
//            for (Member member : result) {
//                System.out.println("member = " + member);
//            }

            // 벌크 연산
            int resultCount = em.createQuery("UPDATE Member m SET m.age = 20 ")
                    .executeUpdate();

            System.out.println("resultCount = " + resultCount);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();

            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }
}