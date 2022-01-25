package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf= Persistence.createEntityManagerFactory("hello");
        //하나만 생성되서 애플리케이션 전체에 공유

        EntityManager em = emf.createEntityManager();
        //쓰레드간에 공유 노노

        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try{
            Member member1=new Member();
            member1.setId(9L);
            member1.setName("hello");

            Member member2=new Member();
            member2.setId(99L);
            member2.setName("hello2");

            em.persist(member1);
            em.persist(member2);

            Member findMember=em.find(Member.class,1L);
            findMember.setName("HOHOHOkkkkk"); //이렇게 수정해도 DB값이 바뀐다. 놀랍다!!!
            System.out.println("findMember = " + findMember.getName());

            List<Member> result=em.createQuery("select m from Member as m",Member.class).getResultList();

            for (Member member : result) {
                System.out.println("member1 = " + member.getName());
            }

            //em.remove(findMember);

            tx.commit();
        }
        catch(Exception e){
            tx.rollback();
        }
        finally {
            em.close();
        }

        emf.close();

    }
}
