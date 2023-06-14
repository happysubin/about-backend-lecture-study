package jpabook.jpashop.service;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    void itemOrder(){
        //given
        Member member=createMember();
        Item item=createBook("JPA",10000,10);
        int orderCount=2;

        //when
        Long orderId= orderService.order(member.getId(),item.getId(),orderCount);

        //then
        Order getOrder=orderRepository.findOne(orderId);

        Assertions.assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.ORDER);
        Assertions.assertThat(1).isEqualTo(getOrder.getOrderItems().size());
        Assertions.assertThat(item.getStockQuantity()).isEqualTo(8);

    }

    @Test
    void faiOrder(){
        try {
            //given
            Member member = createMember();
            Item item = createBook("JPA", 10000, 10);

            int orderCount = 11;

            //when
            orderService.order(member.getId(),item.getId(),orderCount);

        }
        catch(NotEnoughStockException e){
            //then
            Assertions.assertThat(e.getMessage()).isEqualTo("need more stock");
        }
    }


    @Test
    void cancelOrder(){
        //given

        Member member=createMember();
        Item item=createBook("JPA",10000,10);
        int orderCount=2;

        Long orderId= orderService.order(member.getId(), item.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder=orderRepository.findOne(orderId);

        Assertions.assertThat(OrderStatus.CANCEL).isEqualTo(getOrder.getStatus());
        Assertions.assertThat(10).isEqualTo(item.getStockQuantity());


    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123")); em.persist(member);
        return member;
    }
    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setStockQuantity(stockQuantity);
        book.setPrice(price);
        em.persist(book);
        return book;
    }

}