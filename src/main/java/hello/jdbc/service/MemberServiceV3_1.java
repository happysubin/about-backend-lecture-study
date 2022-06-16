package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionStatus;

import java.sql.SQLException;

/**
 *
 * 트랜잭션을 사용한다. 파라미터 연동 및 풀을 고려
 */

@Slf4j
@RequiredArgsConstructor
public class MemberServiceV3_1 {
    private final MemberRepositoryV3 memberRepository;
    private final PlatformTransactionManager transactionManager;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        //트랜잭션 시작
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try{

            bizLogic(fromId, toId, money);
            transactionManager.commit(status);
        }
        catch(Exception e){
            transactionManager.rollback(status);
            throw new IllegalStateException(e);
        }
    }// 트랜잭션이 커밋되거나 롤백되면 트랜잭션 매니저 내부에서 커넥션을 알아서 다 정리해준다.

    private void bizLogic(String fromId, String toId, int money) throws SQLException {
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