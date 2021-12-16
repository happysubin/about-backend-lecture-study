package spring.core.discount;

import spring.core.member.Grade;
import spring.core.member.Member;

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
