package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepository;
import hello.jdbc.repository.MemberRepositoryV4_1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

/**
 * 예외 누수 문제 해결
 * SQLException 제거
 *
 * MemberRepository 인터페이스 의존 */

@Slf4j
public class MemberServiceV4 {

    private final MemberRepositoryV4_1 memberRepository;

    public MemberServiceV4(MemberRepositoryV4_1 memberRepository) {
        this.memberRepository = memberRepository;
    }

   @Transactional //성공하면 커밋, 런타임 에러가 발생하면 롤백
    public void accountTransfer(String fromId, String toId, int money )  {
        bizLogic(fromId, toId, money);
    }

    private void bizLogic(String fromId, String toId, int money)  {
        //비즈니스 로직
        Member fromMember = memberRepository.findById(fromId);
        Member toMember = memberRepository.findById(toId);

        memberRepository.update(fromId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.update(toId, toMember.getMoney() + money);
    }

    private void validation(Member toMember){
        if(toMember.getMemberId().equals("ex")){
            throw new IllegalStateException("이체 중 예외가 발생하였습니다");
        }
    }
}