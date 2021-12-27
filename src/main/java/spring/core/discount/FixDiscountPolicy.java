package spring.core.discount;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import spring.core.member.Grade;
import spring.core.member.Member;


@Component
@Qualifier("subDiscountPolicy")
public class FixDiscountPolicy implements DiscountPolicy{

    private int discountFixAmount=1000;

    @Override
    public int discount(Member member, int price) {
        if(member.getGrade()== Grade.VIP){ //enum은 ==이 맞다.
            return discountFixAmount;
        }else {
            return 0;
        }
    }
}
