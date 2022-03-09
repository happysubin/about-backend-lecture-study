package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.type.classreading.MethodsMetadataReader;
import study.datajpa.domain.Member;
import study.datajpa.dto.MemberDto;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> { //타입과 아이디

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);
   //인터페이스 추상메소드만 있는데 돌아간다..? 대박!!!
    //스프링 데이터 JPA는 메소드 이름을 분석해서 JPQL을 생성하고 실행. 짧막 짧막한 쿼리에서만 용. 복잡한 쿼리는 다른 방법

    //@Query(name = "Member.findByUsername") //주석해도 동작함
    //List<Member> findByUsername(@Param("username") String username);

    @Query("select m from Member m where m.username = :username and m.age = :age") //jpql을 인터페이스 메소드에 바로 작성
    List<Member> findUser(@Param("username") String username, @Param("age") int age); //이 기능을 굉장히 많이 쓴다. 집중!!!
    //JPA Named 쿼리처럼 애플리케이션 실행 시점에 문법 오류를 발견할 수 있음(매우 큰 장점!)

    @Query("select m.username from Member m")
    List<String> findUsernameList(); //사용자 이름만 다 가져오고 싶다면??

    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) " + "from Member m join m.team t") //dto는 늘 new 오퍼레이션을 사용
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.username in :names")// 컬렉션 타입으로 in절 지원
    List<Member> findByNames(@Param("names") List<String> names); // 이거는 실무에서 많이 쓴다고 한다.

    List<Member> findListByUsername(String name); //컬렉션
    Member findByUsername(String name); //단건
    Optional<Member> findOptionalByUsername(String name);
    //반환 타입이 상당히 유연하다.


    Page<Member> findByAge(int age, Pageable pageable);


    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);
}
