package hellojpql;

import jakarta.persistence.*;

import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setName("User1");
            member.setAge(20);

            em.persist(member);

            TypedQuery<Member> query1 = em.createQuery("SELECT m FROM Member m WHERE m.name = :name", Member.class);
            query1.setParameter("name", "User1");

            Member result = em.createQuery("SELECT m FROM Member m WHERE m.name = :name", Member.class)
                    .setParameter("name", "User1")
                    .getSingleResult();

            TypedQuery<String> query2 = em.createQuery("SELECT m.name FROM Member m",  String.class);
            Query query3 = em.createQuery("SELECT m.name, m.age FROM Member m");

            List<Member> resultList = query1.getResultList();
            Member singleResult = query1.getSingleResult();

            for(Member m : resultList) {
                System.out.println("resultList = " + m.getName());
            }

            System.out.println("singleResult = " + singleResult);

            tx.commit();
        }
        catch (Exception e) {
            tx.rollback();

            e.printStackTrace();
        }
        finally {
            em.close();
        }

        emf.close();
    }
}