package spring.core.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spring.core.discount.DiscountPolicy;
import spring.core.discount.FixDiscountPolicy;
import spring.core.discount.RateDiscountPolicy;
import spring.core.member.Member;
import spring.core.member.MemberRepository;
import spring.core.member.MemoryMemberRepository;


@Component
public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    @Autowired //여러 의존관계도 한번에 주입 가능
    public OrderServiceImpl(DiscountPolicy discountPolicy,MemberRepository memberRepository) {
        this.discountPolicy = discountPolicy;
        this.memberRepository=memberRepository;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member=memberRepository.findById(memberId);
        int discountPrice=discountPolicy.discount(member,itemPrice);

        return new Order(memberId,itemName,itemPrice,discountPrice);
    }

    //test 용
    public MemberRepository getMemberRepository(){
        return memberRepository;
    }
}