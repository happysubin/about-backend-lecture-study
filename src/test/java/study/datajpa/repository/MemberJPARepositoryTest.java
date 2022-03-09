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

    @Test
    public void findByUsernameAndAgeGreaterThan() {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);
        memberJPARepository.save(m1);
        memberJPARepository.save(m2);

        List<Member> result = memberJPARepository.findByUsernameAndAgeGreaterThen("AAA", 15);

        assertThat(result.get(0).getUsername()).isEqualTo("AAA");
        assertThat(result.get(0).getAge()).isEqualTo(20);
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void testNamedQuery(){
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);
        memberJPARepository.save(m1);
        memberJPARepository.save(m2);

        List<Member> result = memberJPARepository.findByUsername("AAA");
        Member member = result.get(0);

        assertThat(member).isEqualTo(m1);
    }
    //스프링 데이터 JPA를 사용하면 실무에서 Named Query를 직접 등록해서 사용하는 일은 드물다고 한다.

    @Test
    public void paging() throws Exception {
        memberJPARepository.save(new Member("member1", 10));
        memberJPARepository.save(new Member("member2", 10));
        memberJPARepository.save(new Member("member3", 10));
        memberJPARepository.save(new Member("member4", 10));
        memberJPARepository.save(new Member("member5", 10));
        int age = 10;
        int offset = 0;
        int limit = 3;

        List<Member> members = memberJPARepository.findByPage(age, offset, limit);
        long totalCount = memberJPARepository.totalCount(age);

        Assertions.assertThat(members.size()).isEqualTo(3);
        Assertions.assertThat(totalCount).isEqualTo(5);
    }

    @Test
    public void bulkUpdate() throws Exception {
        //given
        memberJPARepository.save(new Member("member1", 10));
        memberJPARepository.save(new Member("member2", 19));
        memberJPARepository.save(new Member("member3", 20));
        memberJPARepository.save(new Member("member4", 21));
        memberJPARepository.save(new Member("member5", 40));
        //when
        int resultCount = memberJPARepository.bulkAgePlus(20);
        //then
        assertThat(resultCount).isEqualTo(3);
    }


}