package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


//@SpringBootTest
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;


    @Test
    @Transactional
    @Rollback(false)
    void testMember(){
        Member member=new Member();
        member.setUsername("memberA");

        Long saveId= memberRepository.save(member);
        Member findMember=memberRepository.find(saveId);

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);

    }
}