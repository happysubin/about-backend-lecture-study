package spring.core;

import spring.core.member.Grade;
import spring.core.member.Member;
import spring.core.member.MemberService;
import spring.core.member.MemberServiceImpl;
import spring.core.order.Order;
import spring.core.order.OrderService;
import spring.core.order.OrderServiceImpl;

public class OrderApp {
    public static void main(String[] args) {
        AppConfig appConfig=new AppConfig();
        MemberService memberService= appConfig.memberService();
        OrderService orderService= appConfig.orderService();
        long memberId=1L;
        Member member=new Member(1L,"memberA", Grade.VIP);
        memberService.join(member);

        Order order=orderService.createOrder(1L,"itemA",10000);
        System.out.println("order = " + order);
    }
}
