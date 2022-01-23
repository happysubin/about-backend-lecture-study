package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;



@Transactional //반복 가능한 테스트 지원, 각각의 테스트를 실행할 때마다 트랜잭션을 시작하고 테스트가 끝나면 트랜잭션을 강제로 롤백
@SpringBootTest
class MemberServiceTest {

    @Autowired private  MemberService memberService;
    @Autowired private MemberRepository memberRepository;

    @Test
    void save() throws Exception{

        //given
        Member member=new Member();
        member.setName("memberA");

        //when
        Long saveId=memberService.join(member);

        //then
        assertEquals(member,memberRepository.findOne(saveId));
    }

    @Test
    void 중복회원예외(){
        try {
            //given
            Member member1 = new Member();
            member1.setName("kim");
            Member member2 = new Member();
            member2.setName("kim");

            //When
            memberService.join(member1);
            memberService.join(member2); //예외가 발생해야 한다.
        }
        catch(IllegalStateException e){
            //then
            Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        }
    }

}