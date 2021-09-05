package hello.hello.world.spring.Service;


import hello.hello.world.spring.domain.Member;
import hello.hello.world.spring.repository.MemberRepository;
import hello.hello.world.spring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


//커맨드 쉬프트 T를 누르면 저절로 테스트 코드가 만들어진다.
@SpringBootTest //이 키워드로 스프링 테스트가 가능하다.
@Transactional //롤백함 디비의 실제 데이터가 반영이 안됨. 다음 테스트를 반복해서 실행 가능. 기존 처럼 지우는 코드를 안넣어도됨
//테스트 케이스에 이 애노테이션이 있으면, 테스트 시작 전에 트랜잭션을 시작하고,
//테스트 완료 후에 항상 롤백한다. 이렇게 하면 DB에 데이터가 남지 않으므로 다음 테스트에 영향을 주지
//않는다.
class MemberServiceIntegrationTest {
    @Autowired MemberService memberService;
    @Autowired  MemberRepository memberRepository;
    //테스트 코드는 편하게 편하게 Autowired로 받는 걸로 편하다.

    /*
    @BeforeEach //동작하기전에 실행 테스트는 독립적으로 실행되어야한다.
    public void beforeEach(){
        memberRepository=new MemoryMemberRepository();
        memberService=new MemberService(memberRepository);
        //테스트 실행시 각각 생성
   }
   */


/*
    @AfterEach //어떤 메소드가 끝나면 실행된다. 콜백 메소드 밑에 메소드가 하나 끝나면 호출된다.
    public void afterEach(){ //끝나고나면 db의 값을 날려줌
        memberRepository.clearStore(); //이러면 각각의 메소드가 끝나면 매번 실행된다.
    }
*/
    @Test
    void join() {

        //given 주어졌을때
        Member member=new Member();
        member.setName("hello");

        //when  이걸 실행했을 때
        Long saveId=memberService.join(member);

        //then 결과가 이게 나와야해
        Member findMember=memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외(){
        //given
        Member member1=new Member();
        member1.setName("spring");
        Member member2=new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        IllegalStateException e= assertThrows(IllegalStateException.class,()->memberService.join(member2));
        //오른쪽 로직을 실행하는데 왼쪽상황이 터져야함.
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");
        //테스트 코드 성공!

        /*try catch 문법으로도 검증가능
        try {
            memberService.join(member2);
            fail();
        }
        catch(IllegalStateException e){
            Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");
        }
        */

        //then
    }


}

//스프링 컨테이너 없이 테스트하는게 좋은 방법일 것이다 보통.