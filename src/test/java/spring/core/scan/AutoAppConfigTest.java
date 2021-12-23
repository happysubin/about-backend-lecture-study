package spring.core.scan;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.core.AutoAppConfig;
import spring.core.member.MemberService;

public class AutoAppConfigTest {

    @Test
    void basicBean(){
        AnnotationConfigApplicationContext ac=new AnnotationConfigApplicationContext(AutoAppConfig.class);

        MemberService memberService=ac.getBean(MemberService.class);
        String[] names=ac.getBeanDefinitionNames();

        for(String name:names){
            System.out.println("name = " + name);
        }

        Assertions.assertThat(memberService).isInstanceOf(MemberService.class);
    }


}

/**
 * @ComponentScan 은 @Component 가 붙은 모든 클래스를 스프링 빈으로 등록한다.
 * 이때 스프링 빈의 기본 이름은 클래스명을 사용하되 맨 앞글자만 소문자를 사용한다.
 * 빈 이름 기본 전략: MemberServiceImpl 클래스 memberServiceImpl
 * 빈 이름 직접 지정: 만약 스프링 빈의 이름을 직접 지정하고 싶으면
 * @Component("memberService2") 이런식으로 이름을 부여하면 된다.
 *
 *
 * 생성자에 @Autowired 를 지정하면, 스프링 컨테이너가 자동으로 해당 스프링 빈을 찾아서 주입한다.
 * 이때 기본 조회 전략은 타입이 같은 빈을 찾아서 주입한다.
 *
 */



/**
 * 이 경우 수동 빈 등록이 우선권을 가진다.
 * (수동 빈이 자동 빈을 오버라이딩 해버린다.)
 *
 *  최근 스프링 부트에서는 수동 빈 등록과 자동 빈 등록이 충돌나면 오류가 발생하도록 기본 값을
 * 바꾸었다. 스프링 부트인 CoreApplication 을 실행해보면 오류를 볼 수 있다
 */