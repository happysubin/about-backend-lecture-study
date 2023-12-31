package spring.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.core.discount.DiscountPolicy;
import spring.core.discount.FixDiscountPolicy;
import spring.core.discount.RateDiscountPolicy;
import spring.core.member.MemberRepository;
import spring.core.member.MemberService;
import spring.core.member.MemberServiceImpl;
import spring.core.member.MemoryMemberRepository;
import spring.core.order.OrderService;
import spring.core.order.OrderServiceImpl;

@Configuration // 애플리케이션 구성, 설정 정보. 이러한 설정정보에 @Configuration 이라 적는다.
public class AppConfig {

    @Bean //Bean 을 적으면 스프링 컨테이너에 등록이 된다.
    public MemberService memberService(){

        //1번
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }
    @Bean
    public MemberRepository memberRepository() {

        //2번 3번?
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService(){
        //1번
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(discountPolicy(),memberRepository());

    }

    @Bean
    public DiscountPolicy discountPolicy(){
        return new RateDiscountPolicy();
    }
}

//실제 동작에 필요한 구현 객체는 모조리 여기서 생성.
//AppConfig 는 생성한 객체 인스턴스의 참조를 생성자를 통해서 주입한다.

//MemoryRepository 중복 제거
//리팩토링 시 큰 장점. 역할이 잘 드러난다. 메소드 명을 보면 역할 파악!
//나중에 memberRepository를 바꾸면 저 코드만 바꾸면 된다. 수정에 용이


//AppConfig 로 코드가 사용 영역과 구성영역으로 나뉜다.
//사용 영역의 어떤 코드도 변경할 필요가 없다. 구성 영역(AppConfig)만 조금 수정!


// ConfigurationSingletonTest를 돌리면 놀랍게도 MemberRepository 는 1번만 호출된다. 아래가 출력 결과다
/**스프링 컨테이너가 각각 @Bean을 호출해서 스프링 빈을 생성한다. 그래서 memberRepository() 는
 다음과 같이 총 3번이 호출되어야 하는 것 아닐까?
 *
 * call AppConfig.memberService
 * call AppConfig.memberRepository
 * call AppConfig.orderService
 *
 *
 */