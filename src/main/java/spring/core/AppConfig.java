package spring.core;

import spring.core.discount.RateDiscountPolicy;
import spring.core.member.MemberService;
import spring.core.member.MemberServiceImpl;
import spring.core.member.MemoryMemberRepository;
import spring.core.order.OrderService;
import spring.core.order.OrderServiceImpl;



public class AppConfig {
    public MemberService memberService(){
        return new MemberServiceImpl(new MemoryMemberRepository());
    }

    public OrderService orderService(){
        return new OrderServiceImpl(new RateDiscountPolicy(),new MemoryMemberRepository());
    }
}

//실제 동작에 필요한 구현 객체는 모조리 여기서 생성.
//AppConfig 는 생성한 객체 인스턴스의 참조를 생성자를 통해서 주입한다.