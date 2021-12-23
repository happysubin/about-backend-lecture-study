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