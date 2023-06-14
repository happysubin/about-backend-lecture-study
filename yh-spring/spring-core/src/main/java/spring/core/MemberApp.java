package spring.core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.core.member.Grade;
import spring.core.member.Member;
import spring.core.member.MemberService;
import spring.core.member.MemberServiceImpl;

public class MemberApp {
    public static void main(String[] args) {
//        AppConfig appConfig=new AppConfig();
//        MemberService memberService= appConfig.memberService();

        ApplicationContext applicationContext=new AnnotationConfigApplicationContext(AppConfig.class);
        //이러면 AppConfig 환경설정 정보를 가지고 Bean들을 스프링 컨테이너에 넣어서 관리해준다.

        MemberService memberService=applicationContext.getBean("memberService",MemberService.class);
        //메소드 이름으로 등록된 빈을 가져온다.

        Member memberA=new Member(1L, "memberA",Grade.VIP);
        memberService.join(memberA);

        Member findMember=memberService.findMember(1L);
        System.out.println("findMember = " + findMember.getName());
        System.out.println("memberA = " + memberA.getName());
    }
}
