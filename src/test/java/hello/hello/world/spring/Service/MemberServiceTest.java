package hello.hello.world.spring.Service;


import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
// 위 방식으로하면 동일한 이름의 Assertions를 쓰지않고 하위 메소드를 이름으로 바로 사용가능
import hello.hello.world.spring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import hello.hello.world.spring.domain.Member;


//커맨드 쉬프트 T를 누르면 저절로 테스트 코드가 만들어진다.
class MemberServiceTest {
    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach //동작하기전에 실행 테스트는 독립적으로 실행되어야한다.
    public void beforeEach(){
        memberRepository=new MemoryMemberRepository();
        memberService=new MemberService(memberRepository);
        //테스트 실행시 각각 생성
   }

    @AfterEach //어떤 메소드가 끝나면 실행된다. 콜백 메소드 밑에 메소드가 하나 끝나면 호출된다.
    public void afterEach(){ //끝나고나면 db의 값을 날려줌
        memberRepository.clearStore(); //이러면 각각의 메소드가 끝나면 매번 실행된다.
    }

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

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}