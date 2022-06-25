package hello.itemservice.propagation;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    LogRepository logRepository;

    /**
     * MemberService    @Transactional:OFF
     * MemberRepository @Transactional:ON
     * LogRepository    @Transactional:ON
     */

    @Test
    void outerTxOff_success() {
        //given
        String username = "outerTxOff_success";
        //when
        memberService.joinV1(username);
        //then: 모든 데이터가 정상 저장된다.

        assertTrue(memberRepository.find(username).isPresent());
        assertTrue(logRepository.find(username).isPresent());
    }

    /**
     * 둘다 레포지토리 단에서 트랜잭션이 걸린다. 따라서 각각 커넥션을 가져서 커밋을 한다.
     * 물론 테스트에 트랜잭션 애노테이션을 안걸어서 롤백되지는 않는다.
     */

    @Test
    void outerTxOff_fail() {
        //given
        String username = "로그예외_outerTxOff_fail";
        //when
        assertThatThrownBy(() -> memberService.joinV1(username)).isInstanceOf(RuntimeException.class);
        //then: 완전히 롤백되지 않고, member 데이터가 남아서 저장된다.
        assertTrue(memberRepository.find(username).isPresent());
        assertTrue(logRepository.find(username).isEmpty());
    }
    /**
     * 레포지토리단에서 트랜잭션을 걸린다. 따라서 커넥션을 각각 가진다.
     * 로그 레포지토리는 롤백을 하고, 멤버 레포지토리는 커밋을 한다. 커넥션이 다르기 때문에!!!!
     */

    @Test
    void singleTx() {
        //given
        String username = "singleTx";
        //when
        memberService.joinV1(username);
        //then: 모든 데이터가 정상 저장된다.
        assertTrue(memberRepository.find(username).isPresent());
        assertTrue(logRepository.find(username).isPresent());
    }
    //서비스 메서드에 트랜잭션 애노테이션이 걸렸으므로, 같은 커넥션을 사용한다!!!
    //대신 서비스 객체에 트랜잭션 프록시가 적용된다. 레포지토리에는 트랜잭션 프록시가 적용되지 않는다.

}