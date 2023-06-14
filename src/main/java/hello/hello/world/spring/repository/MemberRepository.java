package hello.hello.world.spring.repository;

import hello.hello.world.spring.domain.Member;
import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member); //회원 저장
    Optional<Member> findById(Long id); //id 값으로 회원 불러오기
    //optional은 null을 처리하는 일종의 기법이다.
    Optional<Member> findByName(String name); //이름으로 회원 가져오기
    List<Member> findAll(); //모든 회원 리스트를 가져온다
}
