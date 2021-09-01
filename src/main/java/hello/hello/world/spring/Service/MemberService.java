package hello.hello.world.spring.Service;

import java.util.List;
import hello.hello.world.spring.domain.Member;
import hello.hello.world.spring.repository.MemberRepository;
import hello.hello.world.spring.repository.MemoryMemberRepository;

import java.util.Optional;

public class MemberService {
    //MemberRepository는 인터페이스 앞 인터페이스를 implements 한 MemoryMemberRepository는 클래스이다.
    private final MemberRepository memberRepository=new MemoryMemberRepository();

    //회원가입
    public Long join(Member member){
        //같은 이름 중복회원은 가입 불가
        /*
        Optional<Member> result=memberRepository.findByName(member.getName());
        result.ifPresent(m->{throw new IllegalStateException("이미 존재하는 회원입니다");});//값이 있다면
        */
        //result.orElseGet() 많이 쓴다 값이 있으면 꺼내고 없으면 어떤 메소드를 실행.
        //Optional이라 이 로직이 동작함.
        validateDuplicateMember(member); //중복회원 검증 통과하면 바로 가입
        memberRepository.save(member);
        return member.getID();
    }
    private void validateDuplicateMember(Member member){
        //위 논리도 좋지만 깔끔하게 정리

        memberRepository.findByName(member.getName())
                .ifPresent(m->{throw new IllegalStateException("이미 존재하는 회원입니다");});
    }

    //전체회원 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
