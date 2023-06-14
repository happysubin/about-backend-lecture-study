package hello.hello.world.spring.repository;


import hello.hello.world.spring.domain.Member;
import hello.hello.world.spring.repository.MemberRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

//implements 는 interface 상속시 쓰이는 키워드다.
//@Repository //레포지토리 키워드를 보고 스프링 컨테이너에 등록
public class MemoryMemberRepository implements MemberRepository {
    private static Map<Long,Member> store=new HashMap<>();
    private static long sequence=0L;//키 값 생성

    @Override //오버라이딩: 상속받은 메서드의 내용을 변경하는 것
    public Member save(Member member) { //객체를 인자로 받는다
        member.setId(++sequence);//전위 연산자. 객체에 아이디를 설정
        store.put(member.getId(),member); //아이디를 가져와서 그 객체와 함꼐 저장
        System.out.println(member.getId());
        return member; //멤버를 리턴
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
        //Null 을 처리하는 방법
    }

    @Override
    public Optional<Member> findByName(String name) {
       return store.values().stream().filter(member -> member.getName().equals(name)).findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear(); //이 clear 메소드는 store를 다 비운다.
    }
}

//자바 실무는 List 를 많이 사용한다.
