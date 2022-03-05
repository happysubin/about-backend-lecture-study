package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.domain.Member;

@SpringBootTest
@Transactional
class MemberJPARepositoryTest {

    @Autowired
    MemberJPARepository memberJPARepository;

    @Test
    void test(){
        Member member=new Member();
        Member saveMember = memberJPARepository.save(member);

        Member member1 = memberJPARepository.find(saveMember.getId());

        Assertions.assertThat(saveMember).isEqualTo(member1);
    }
}