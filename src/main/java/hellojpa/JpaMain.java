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

            Member member=new Member();
            member.setId(1L);
            member.setName("userA");
            member.setRoleType(RoleType.USER);
            em.persist(member);
            //비영속

            //Member member1=new Member();
            //member1.setId(9L);
            //member1.setName("hello");

            //Member member2=new Member();
            //member2.setId(200L);
            //member2.setName("hello2");

            //영속
            //em.persist(member1);
            //em.persist(member2);

            //em.flush(); 이렇게하면 데베에 인서트 쿼리가 이 시점에 출발한다.
            //flush를 해도 1차 캐시는 지워지지 않는다.

            //em.detach(member) //이러면 영속성 컨텍스트에서 관리받지 않는다.

            //Member findMember=em.find(Member.class,1L);
            //findMember.setName("HOHOHOkkkkk"); //이렇게 수정해도 DB값이 바뀐다. 놀랍다!!!
            //System.out.println("findMember = " + findMember.getId());//1차 캐시에 의해 셀렉트 쿼리가 안나가고도 찾아진다.
            //System.out.println("findMember = " + findMember.getName());//즉 캐시에서 찾는다는 말

            //Member findMember2=em.find(Member.class,3L);//쿼리 나감. 참고로 위와 아래의 객체는 같은 객체. 동일성 보장
            //Member findMember3=em.find(Member.class,3L);//캐시에서 찾아서 쿼리가 나가지 않음

            //List<Member> result=em.createQuery("select m from Member as m",Member.class).getResultList();



            //em.remove(findMember);

            tx.commit(); //커밋하는 순간 DB에 insert sql을 보낸다. 명심하자!!!!!!
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
