package hello.hello.world.spring.Service;

import java.util.List;
import hello.hello.world.spring.domain.Member;
import hello.hello.world.spring.repository.MemberRepository;
import hello.hello.world.spring.repository.MemoryMemberRepository;

import java.util.Optional;

//커맨드 쉬프트 T 를 누르면 테스트 코드를 편하게 작성가능
public class MemberService {
    //MemberRepository는 인터페이스 앞 인터페이스를 implements 한 MemoryMemberRepository는 클래스이다.
    //테스트 코드 수행시 구현된 파일과 테스트 코드 파일에서 동일한 DB를 공유해야하기 때문에 수정!
    //두파일에서 new 연산자를 쓰면 아예 동떨어진게 되어버린다.
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository){
        this.memberRepository=memberRepository;
        //멤버서비스 입장에서 외부에서 멤버 리포지토리 값을 넣어준다. 이런걸을 Dependency injection (DI) 라고 한다.
    }

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
