package hello.hello.world.spring;

import hello.hello.world.spring.Service.MemberService;
import hello.hello.world.spring.repository.JdbcMemberRepository;
import hello.hello.world.spring.repository.JdbcTemplateMemberRepository;
import hello.hello.world.spring.repository.MemberRepository;
import hello.hello.world.spring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import javax.swing.*;

@Configuration //스프링이 뜰 때 이걸 읽고
public class SpringConfig {

    private DataSource dataSource;

    //스프링이 우리가 설정한  application.properties를 보고 (DI) 생성자에 주입을 해준다.
    //스프링이 자체적으로도 빈 생성
    @Autowired
    public SpringConfig(DataSource dataSource){
        this.dataSource=dataSource;
    }

    @Bean //이거는 스프링 빈에 등록하라는 거라고 인식함. 멤버 서비스를 호출해서 스프링 빈에 등록
    public MemberService memberService(){
        return new MemberService(memberRepository());
        //이 멤버 서비스가 멤버 컨트롤로 들어감.
    }
    @Bean //얘도 스프링 빈에 올라감
    public MemberRepository memberRepository(){

        //return new JdbcMemberRepository(dataSource);
        return new JdbcTemplateMemberRepository(dataSource);
    }
}

//DI는 필드주입, Setter 주입, 생성자 주입이 있다. 의존관계가 실행 중에 동적으로 변하지 않아서 생성자 주입을 사용하자!!!
//실무는 주로 컴포넌트 스캔으로 빈으로 등록!! 그러나 정형화 되지 않거나, 상황에 따라 구현 클래스를 변경해야하면
//설정을 통해 스프링 빈으로 등록한다.


//객체 지향의 장점이 나온다. 위에 메모리 멤버리포지토리를 JDBC멤버리포지토리로 바꾸면 모든게 해결된다. 너무나 간단!