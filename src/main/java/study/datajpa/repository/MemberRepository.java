package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa.domain.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member,Long> { //타입과 아이디

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);
    //인터페이스 추상메소드만 있는데 돌아간다..? 대박!!!
    //스프링 데이터 JPA는 메소드 이름을 분석해서 JPQL을 생성하고 실행. 짧막 짧막한 쿼리에서만 사용. 복잡한 쿼리는 다른 방법

}
