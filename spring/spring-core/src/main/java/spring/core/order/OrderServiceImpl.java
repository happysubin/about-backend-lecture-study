package spring.core.order;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import spring.core.annotation.MainDiscountPolicy;
import spring.core.discount.DiscountPolicy;
import spring.core.discount.FixDiscountPolicy;
import spring.core.discount.RateDiscountPolicy;
import spring.core.member.Member;
import spring.core.member.MemberRepository;
import spring.core.member.MemoryMemberRepository;


@Component
//@RequiredArgsConstructor //final 이 붙은 필드를 모아 자동으로 생성자를 만들어준다 ctrl f12로 생성자가 만들어진것을 확인 가능
public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    //롬복은 정말 많이 사용한다.

    @Autowired //여러 의존관계도 한번에 주입 가능 //Qualifier는 추가 구분자임. 오로지 찾는 용도.
    public OrderServiceImpl(DiscountPolicy discountPolicy,MemberRepository memberRepository) {
        this.discountPolicy = discountPolicy; //@Autowired 는 타입 명으로 검색하고, 이를 실패하면 필드명으로도 검색한다.
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


/**
 *생성자 주입을 거의 무조건 사용해라 !!
 *
 * 대부분의 의존관계 주입은 한번 일어나면 애플리케이션 종료시점까지 의존관계를 변경할 일이 없다. 오히려
 * 대부분의 의존관계는 애플리케이션 종료 전까지 변하면 안된다.(불변해야 한다.)
 * 수정자 주입을 사용하면, setXxx 메서드를 public으로 열어두어야 한다.
 * 누군가 실수로 변경할 수 도 있고, 변경하면 안되는 메서드를 열어두는 것은 좋은 설계 방법이 아니다.
 * 생성자 주입은 객체를 생성할 때 딱 1번만 호출되므로 이후에 호출되는 일이 없다. 따라서 불변하게 설계할
 * 수 있다
 *
 * 생성자 주입을 사용하면 필드에 final 키워드를 사용할 수 있다. 그래서 생성자에서 혹시라도 값이
 * 설정되지 않는 오류를 컴파일 시점에 막아준다. 오직 생성자 주입 방식만 final 키워드를 사용할 수 있다.
 *
 * 생성자 주입 방식을 선택하는 이유는 여러가지가 있지만, 프레임워크에 의존하지 않고, 순수한 자바 언어의
 * 특징을 잘 살리는 방법이기도 하다.
 * 기본으로 생성자 주입을 사용하고, 필수 값이 아닌 경우에는 수정자 주입 방식을 옵션으로 부여하면 된다.
 * 생성자 주입과 수정자 주입을 동시에 사용할 수 있다.
 * 항상 생성자 주입을 선택해라! 그리고 가끔 옵션이 필요하면 수정자 주입을 선택해라. 필드 주입은 사용하지
 * 않는게 좋다.
 *
 */