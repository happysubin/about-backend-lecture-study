package hellojpa.jpashop;

import hellojpa.User;
import hellojpa.RoleType;
import hellojpa.jpashop.domain.Book;
import hellojpa.jpashop.domain.Order;
import hellojpa.jpashop.domain.OrderItem;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf= Persistence.createEntityManagerFactory("hello");
        //하나만 생성되서 애플리케이션 전체에 공유

        EntityManager em = emf.createEntityManager();
        //쓰레드간에 공유 노노

        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try{

            Book book = new Book();
            book.setName("JPA");
            book.setAuthor("kim");

            em.persist(book);

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
