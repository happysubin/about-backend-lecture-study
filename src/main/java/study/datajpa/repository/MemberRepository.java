package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.type.classreading.MethodsMetadataReader;
import study.datajpa.domain.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member,Long> { //타입과 아이디

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);
   //인터페이스 추상메소드만 있는데 돌아간다..? 대박!!!
    //스프링 데이터 JPA는 메소드 이름을 분석해서 JPQL을 생성하고 실행. 짧막 짧막한 쿼리에서만 용. 복잡한 쿼리는 다른 방법

    //@Query(name = "Member.findByUsername") //주석해도 동작함
    //List<Member> findByUsername(@Param("username") String username);

    @Query("select m from Member m where m.username = :username and m.age = :age") //jpql을 인터페이스 메소드에 바로 작성
    List<Member> findUser(@Param("username") String username, @Param("age") int age); //이 기능을 굉장히 많이 쓴다. 집중!!!
    //JPA Named 쿼리처럼 애플리케이션 실행 시점에 문법 오류를 발견할 수 있음(매우 큰 장점!)
}
