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
    
    
    @Test
    void configurationDeep(){
        ApplicationContext ac= new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean=ac.getBean(AppConfig.class); //얘도 빈으로 등록되기는 함

        System.out.println(bean.getClass());
        //spring.core.AppConfig$$EnhancerBySpringCGLIB$$c6986406@54eb2b70 위 코드에서 이런 요상한게 출력된다
        //그런데 예상과는 다르게 클래스 명에 xxxCGLIB가 붙으면서 상당히 복잡해진 것을 볼 수 있다. 이것은 내가
        //만든 클래스가 아니라 스프링이 CGLIB라는 바이트코드 조작 라이브러리를 사용해서 AppConfig
        //클래스를 상속받은 임의의 다른 클래스를 만들고, 그 다른 클래스를 스프링 빈으로 등록한 것이다!
        //그 임의의 다른 클래스가 바로 싱글톤이 보장되도록 해준다
        //@Configuration 을 붙이면 바이트코드를 조작하는 CGLIB 기술을 사용해서 싱글톤을 보장.

    }
}

//원래는 구체 타입으로 꺼내면 좋지 않음. 그냥 편하게 하려고 이케 함
