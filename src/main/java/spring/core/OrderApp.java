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

        //스프링 컨테이너 생성
        ApplicationContext applicationContext=new AnnotationConfigApplicationContext(AppConfig.class);
        //ApplicationContext는 인터페이스 AnnotationConfigApplicationContext는 인터페이스의 구현체
        
        MemberService memberService=applicationContext.getBean("memberService",MemberService.class);
        OrderService orderService=applicationContext.getBean("orderService",OrderService.class);
        //String 이름 만으로는 반환 값이 뭐가 나올지 잘 모른다. 그럼 Object 최상위 클래스로 캐스팅을 해야한다.
        //그러나 String 이름과 반환 클래스타입을 명확히 알려주면 요청한 클래스 타입을 그대로 반환해준다.

        long memberId=1L;
        Member member=new Member(1L,"memberA", Grade.VIP);
        memberService.join(member);

        Order order=orderService.createOrder(1L,"itemA",12000);
        System.out.println("order = " + order);
    }
}