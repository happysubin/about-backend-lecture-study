package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.domain.Member;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberJPARepositoryTest {

    @Autowired
    MemberJPARepository memberJPARepository;

    @Test
    void test(){
        Member member=new Member("userA");
        Member saveMember = memberJPARepository.save(member);

        Member member1 = memberJPARepository.find(saveMember.getId());

        assertThat(saveMember).isEqualTo(member1);
    }

    @Test
    void basicCRUD(){
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberJPARepository.save(member1);
        memberJPARepository.save(member2);

        //조회
        Member findMember1 = memberJPARepository.findById(member1.getId()).get();
        Member findMember2 = memberJPARepository.findById(member2.getId()).get();

        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);
        //리스트 조회 검증
        List<Member> all = memberJPARepository.findAll(); assertThat(all.size()).isEqualTo(2);
        //카운트 검증
        long count = memberJPARepository.count(); assertThat(count).isEqualTo(2);
        //삭제 검증
        memberJPARepository.delete(member1); memberJPARepository.delete(member2);

        long deletedCount = memberJPARepository.count();
        assertThat(deletedCount).isEqualTo(0);
    }
}