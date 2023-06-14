package spring.core.beanFind;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.core.AppConfig;
import spring.core.member.MemberService;
import spring.core.member.MemberServiceImpl;
import static org.junit.jupiter.api.Assertions.*;

public class ApplicationContextBasicFindTest {
    AnnotationConfigApplicationContext ac=new AnnotationConfigApplicationContext(AppConfig.class);
    
    @Test
    @DisplayName("빈 이름으로 조회")
    void findBeanByName(){
        MemberService memberService=ac.getBean("memberService",MemberService.class);
        System.out.println("memberService = " + memberService);
        System.out.println("memberService.getClass() = " + memberService.getClass());

        Assertions.assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("빈 이름없이 타입으로만 조회") //타입이 여러개일때 곤란.
    void findBeanByType(){
        MemberService memberService=ac.getBean(MemberService.class);
        System.out.println("memberService = " + memberService);
        System.out.println("memberService.getClass() = " + memberService.getClass());

        Assertions.assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }
    
    //지금까지 인터페이스로 조회. 이러면 조회할 때 인터페이스의 구현체가 대상이 된다함

    @Test
    @DisplayName("구체타입으로 조회") //역할에 의존해야 하므로 구현에 의존하는 이 코드는 좋지 않다. 이런게 있다!
    void findBeanByInterfaceImpl(){
        MemberServiceImpl memberService=ac.getBean(MemberServiceImpl.class);
        System.out.println("memberService = " + memberService);
        System.out.println("memberService.getClass() = " + memberService.getClass());

        Assertions.assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }


    @Test
    @DisplayName("빈 이름으로 조회 X") //역할에 의존해야 하므로 구현에 의존하는 이 코드는 좋지 않다. 이런게 있다!
    void findBeanByNameX(){
       // MemberService memberService=ac.getBean("XXXX",MemberService.class);
        assertThrows(NoSuchBeanDefinitionException.class,()->
                ac.getBean("XXXX",MemberService.class));
        //오른쪽 로직을 실행하면 왼쪽 에러가 터져야한다.

        //Assertions.assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }
}
