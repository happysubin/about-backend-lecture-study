package study.datajpa.repository;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.domain.Member;
import study.datajpa.domain.Team;
import study.datajpa.dto.MemberDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    @PersistenceContext
    EntityManager em;


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

    @Test
    public void paging() throws Exception {
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));

        int age=10;
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        Page<Member> page = memberRepository.findByAge(age, pageRequest);


        List<Member> content = page.getContent(); //조회된 데이터
        assertThat(content.size()).isEqualTo(3); //조회된 데이터 수
        assertThat(page.getTotalElements()).isEqualTo(5); //전체 데이터 수
        assertThat(page.getNumber()).isEqualTo(0); //페이지 번호
        assertThat(page.getTotalPages()).isEqualTo(2); //전체 페이지 번호
        assertThat(page.isFirst()).isTrue(); //첫번째 항목인가?
        assertThat(page.hasNext()).isTrue(); //다음 페이지가 있는가?
    }
    @Test
    public void bulkUpdate() throws Exception {
//given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 19));
        memberRepository.save(new Member("member3", 20));
        memberRepository.save(new Member("member4", 21));

        memberRepository.save(new Member("member5", 40));
        //when
        int resultCount = memberRepository.bulkAgePlus(20);
//then
        assertThat(resultCount).isEqualTo(3);
    }

    @Test
    public void findMemberLazy() throws Exception {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);
        memberRepository.save(new Member("member1", 10, teamA));
        memberRepository.save(new Member("member2", 20, teamB));
        em.flush();
        em.clear();
        //when
        List<Member> members = memberRepository.findAll();
        //then
        for (Member member : members) {
            member.getTeam().getName();
        }
    }

    @Test
    void entityGraphTest(){
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);
        memberRepository.save(new Member("member1", 10, teamA));
        memberRepository.save(new Member("member2", 20, teamB));
        em.flush();
        em.clear();

        List<Member> all = memberRepository.findAll();
        for (Member member : all) {
            System.out.println("member = " + member);
            System.out.println("member = " + member.getTeam());
        }
    }

    @Test
    public void queryHint() throws Exception {
        //given
        memberRepository.save(new Member("member1", 10));
        em.flush();
        em.clear();
        //when
        Member member = memberRepository.findReadOnlyByUsername("member1");
        member.setUsername("member2");
        em.flush(); //Update Query 실행X } 사실 성능에 큰 차이가 없다고 한다.

    }
}