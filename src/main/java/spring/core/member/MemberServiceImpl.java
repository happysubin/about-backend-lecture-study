package spring.core.member;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component

public class MemberServiceImpl implements  MemberService{

    private final MemberRepository memberRepository;

    //ac.getBean(MemberRepository.class)
    @Autowired //생성자에 이걸 붙이면 스프링이 MemberRepository 타입에 맞는 애를 찾아와서 의존성 주입을 한다. 우리는 메모리멤버 리포지토리를 알아서 주입해줌
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository=memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    //test 용
    public MemberRepository getMemberRepository(){
        return memberRepository;
    }
}

/**
 * 이전에 AppConfig에서는 @Bean 으로 직접 설정 정보를 작성했고, 의존관계도 직접 명시했다. 이제는
 * 이런 설정 정보 자체가 없기 때문에, 의존관계 주입도 이 클래스 안에서 해결해야 한다.
 * @Autowired 는 의존관계를 자동으로 주입해준다.
 *
 */