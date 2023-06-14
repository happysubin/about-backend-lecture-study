package hello.servlet.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


//ctrl shift t 단축키로 테스트 뚝딱

class MemberRepositoryTest {

    MemberRepository memberRepository=MemberRepository.getInstance();

    @AfterEach
    void afterEach(){
        memberRepository.clearStore();
    }

    @Test
    void save(){
        //given
        Member member = new Member("hello", 20);

        //when
        Member savedMember=memberRepository.save(member);

        //then
        Member findMember=memberRepository.findById(savedMember.getId());
        assertThat(findMember).isSameAs(savedMember);

    }

    @Test
    void findAll(){
        //given
        Member member1=new Member("memA",20);
        Member member2=new Member("memB",20);

        memberRepository.save(member1);
        memberRepository.save(member2);

        //when
       List<Member> result=memberRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2); //alt + 엔터는 static import 단축키
        assertThat(result).contains(member1,member2);// 멤버를 가지고 있는가

    }
}