package spring.core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.core.member.Grade;
import spring.core.member.Member;
import spring.core.member.MemberService;
import spring.core.member.MemberServiceImpl;
import spring.core.order.Order;
import spring.core.order.OrderService;
import spring.core.order.OrderServiceImpl;

public class OrderApp {
    public static void main(String[] args) {
//        AppConfig appConfig=new AppConfig();
//        MemberService memberService= appConfig.memberService();
//        OrderService orderService= appConfig.orderService();

        ApplicationContext applicationContext=new AnnotationConfigApplicationContext(AppConfig.class);

        MemberService memberService=applicationContext.getBean("memberService",MemberService.class);
        OrderService orderService=applicationContext.getBean("orderService",OrderService.class);
        long memberId=1L;
        Member member=new Member(1L,"memberA", Grade.VIP);
        memberService.join(member);

        Order order=orderService.createOrder(1L,"itemA",12000);
        System.out.println("order = " + order);
    }
}