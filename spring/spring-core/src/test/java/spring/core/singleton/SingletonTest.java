package spring.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.core.AppConfig;
import spring.core.member.Member;
import spring.core.member.MemberService;

public class SingletonTest {
    @Test
    @DisplayName("스프링 없는 순수한 자바 DI 컨테이너")
    void pureContainer(){
        AppConfig appConfig=new AppConfig();
        //1. 조회 : 호출 할 때마다 객체 생성
        MemberService memberService1=appConfig.memberService();

        //2. 조회 : 호출 할 때마다 객체 생성
        MemberService memberService2= appConfig.memberService();

        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        Assertions.assertThat(memberService1).isNotSameAs(memberService2); //둘이 달라야 한다.
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest(){
        SingletonService instance1= SingletonService.getInstance();
        SingletonService instance2 = SingletonService.getInstance();
        System.out.println("instance1 = " + instance1);
        System.out.println("instance2 = " + instance2);
        Assertions.assertThat(instance1).isSameAs(instance2); //싱글톤 패턴으로 둘이 같아야 한다.

        //private으로 new 키워드를 막아두었다.
        // 호출할 때 마다 같은 객체 인스턴스를 반환하는 것을 확인할 수 있다.
    }

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer(){
        ApplicationContext ac=new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService1=ac.getBean("memberService",MemberService.class);
        MemberService memberService2=ac.getBean("memberService",MemberService.class);

        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        Assertions.assertThat(memberService1).isSameAs(memberService2); //같은 객체라고 나온다

    }
}

/**
 * 우리가 만들었던 스프링 없는 순수한 DI 컨테이너인 AppConfig는 요청을 할 때 마다 객체를 새로 생성한다.
 * 고객 트래픽이 초당 100이 나오면 초당 100개 객체가 생성되고 소멸된다! 메모리 낭비가 심하다.
 * 해결방안은 해당 객체가 딱 1개만 생성되고, 공유하도록 설계하면 된다. 이게 싱글톤 패턴
 */


/***
 * 스프링 컨테이너는 싱글턴 패턴을 적용하지 않아도, 객체 인스턴스를 싱글톤으로 관리한다.
 * 이전에 설명한 컨테이너 생성 과정을 자세히 보자. 컨테이너는 객체를 하나만 생성해서 관리한다.
 * 스프링 컨테이너는 싱글톤 컨테이너 역할을 한다. 이렇게 싱글톤 객체를 생성하고 관리하는 기능을 싱글톤
 * 레지스트리라 한다.
 * 스프링 컨테이너의 이런 기능 덕분에 싱글턴 패턴의 모든 단점을 해결하면서 객체를 싱글톤으로 유지할 수
 * 있다.
 * 싱글톤 패턴을 위한 지저분한 코드가 들어가지 않아도 된다.
 * DIP, OCP, 테스트, private 생성자로 부터 자유롭게 싱글톤을 사용할 수 있다
 *
 *
 * 스프링 컨테이너 덕분에 고객의 요청이 올 때 마다 객체를 생성하는 것이 아니라, 이미 만들어진 객체를
 * 공유해서 효율적으로 재사용할 수 있다.
 */