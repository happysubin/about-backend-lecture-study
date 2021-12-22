package spring.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.core.AppConfig;
import spring.core.member.MemberRepository;
import spring.core.member.MemberServiceImpl;
import spring.core.order.OrderServiceImpl;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest(){

        ApplicationContext ac= new AnnotationConfigApplicationContext(AppConfig.class);

         MemberServiceImpl memberService= ac.getBean("memberService",MemberServiceImpl.class);
        OrderServiceImpl orderService=ac.getBean("orderService",OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();

        System.out.println("memberRepository1 = " + memberRepository1);
        System.out.println("memberRepository2 = " + memberRepository2);
        System.out.println("memberRepository = "+ memberRepository);
        // 정말 놀랍게도 3개 다 같은 객체다. 즉 이 말은 저장된 주소가 같다는 소리. new 로 생성했는데 이게 어떻게 된거지?

        Assertions.assertThat(memberRepository).isSameAs(memberRepository1);
        Assertions.assertThat(memberRepository).isSameAs(memberRepository2);
    }
}

//원래는 구체 타입으로 꺼내면 좋지 않음. 그냥 편하게 하려고 이케 함
