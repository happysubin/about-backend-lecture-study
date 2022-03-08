package study.datajpa.repository;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.domain.Member;
import study.datajpa.domain.Team;
import study.datajpa.dto.MemberDto;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamRepository teamRepository;


    @Test
    void test(){
        Member member = new Member("hello");
        Member savedMember = memberRepository.save(member);
        Member findMember = memberRepository.findById(savedMember.getId()).get(); //이게 좋은 방법은 아님.

        Assertions.assertThat(findMember.getId()).isEqualTo(savedMember.getId());
        Assertions.assertThat(findMember).isEqualTo(savedMember);


    }

    @Test  public void findByUsernameAndAgeGreaterThan() {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15);

        assertThat(result.get(0).getUsername()).isEqualTo("AAA");
        assertThat(result.get(0).getAge()).isEqualTo(20);
        assertThat(result.size()).isEqualTo(1);
    }

    @Test  public void testQuery() {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findUser("AAA", 10);

        assertThat(result.get(0)).isEqualTo(m1);
    }

    @Test
    void findUsernameList(){
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<String> result = memberRepository.findUsernameList();

        for (String s : result) {
            System.out.println("s = " + s);
        }

    }

    @Test
    void findMemberDto(){
        Team team = new Team("teamA");
        teamRepository.save(team);

        Member m1 = new Member("AAA",10);
        m1.setTeam(team);
        memberRepository.save(m1);

        List<MemberDto> usernameList=memberRepository.findMemberDto();
        for (MemberDto dto : usernameList) {
            System.out.println("dto = " + dto);
        }
    }
    @Test
    void findMembers(){
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);
        
        List<Member> usernameList = memberRepository.findByNames(Arrays.asList("AAA","BBB"));

        for (Member member : usernameList) {
            System.out.println("member.getUsername() = " + member.getUsername());
        }
    }

    @Test
    void returnType(){
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> usernameList = memberRepository.findListByUsername("AAA");
        Member member = memberRepository.findByUsername("AAA");
        Optional<Member> aaa = memberRepository.findOptionalByUsername("AAA");

        for (Member member1 : usernameList) {
            System.out.println("member1 = " + member1.getUsername());
        }
        System.out.println("member.getUsername() = " + member.getUsername());
        System.out.println("aaa.get().getUsername() = " + aaa.get().getUsername());
        //정말 유연하게 반환 타입을 지원한다.

        //조회 결과가 많거나 없으면?
        // 컬렉션
        //결과 없음: 빈 컬렉션 반환

        // 단건 조회
        //결과 없음: null 반환
        //결과가 2건 이상: javax.persistence.NonUniqueResultException 예외 발생
        

    }
}