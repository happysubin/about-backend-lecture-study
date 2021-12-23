package spring.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import spring.core.member.MemberRepository;
import spring.core.member.MemoryMemberRepository;

@Configuration
@ComponentScan(
        //컴포넌트 스캔으로 컴포넌트를 다 찾으면서 뺄 것을 지정
        excludeFilters = @ComponentScan.Filter(type= FilterType.ANNOTATION,classes=Configuration.class),
        basePackages="spring.core" //탐색할 패키지의 시작위치를 지정
        //basePackages = {"hello.core", "hello.service"} 이렇게 여러 시작 위치를 지정할 수도 있다
        //만약 지정하지 않으면 @ComponentScan 이 붙은 설정 정보 클래스의 패키지가 시작 위치가 된다.

)
//appConfig 는 등록하지 않는다. 즉 Configuration 애노테이션이 붙은 것을 뺀다. 충돌을 방지하기 위해
// Configuration 내부를 보면 @Component라는 애노테이션이 붙어 있다. 이로 인해 자동으로 등록되니 이를 예방
//실무에서는 안이러는데 우리는 우리 실습 예제를 위해 한 것! (예제 코드를 안 지우기 위해)
public class AutoAppConfig {

    @Bean(name="memoryMemberRepository") //수동 빈 등록으로 자동 빈 등록 이름과 충돌이 발생
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

}
