package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void join(){
        //Given
        Member member=new Member();
        member.setName("ik");

        //Then
        Long saveId=memberService.join(member);

        //then
        Assertions.assertThat(member).isSameAs(memberRepository.findOne(saveId));
    }

    @Test
    public void validateDuplicateMember(){
        try {
            //Given
            Member member1 = new Member();
            member1.setName("kim");
            Member member2 = new Member();
            member2.setName("kim");

            //When
           memberService.join(member1);
           memberService.join(member2);

        }catch(IllegalStateException e){
            //Then
            Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }

    }


}