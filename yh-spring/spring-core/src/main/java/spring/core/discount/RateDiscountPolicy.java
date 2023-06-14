package spring.core.discount;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import spring.core.annotation.MainDiscountPolicy;
import spring.core.member.Grade;
import spring.core.member.Member;

@Primary //이건 동일한 타입들 중에서 이것이 우선된다는 소리.
@Component
@MainDiscountPolicy
public class RateDiscountPolicy implements DiscountPolicy {

    private int discountPercent=10;
    @Override
    public int discount(Member member, int price) {
        if(member.getGrade()== Grade.VIP){
            return price*discountPercent/100; //10퍼 할인
        }else{
            return 0;
        }
    }
}
