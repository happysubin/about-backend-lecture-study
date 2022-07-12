package hello.aop.internalcall;

import hello.aop.internalcall.aop.CallLogAspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@Import(CallLogAspect.class)
@SpringBootTest
class CallServiceV0Test {

    @Autowired
    CallServiceV0 callServiceV0;

    @Test
    void external(){
        callServiceV0.external();
    }

    @Test
    void internal(){
        callServiceV0.internal();
    }
}
/**
 * 이전에 스프링 DB 파트에서도 배운 this 관련 문제다. 프록시 객체, 타겟 객체에 관한 내부 호출 로직에 대해서 기억할 것!!!!
 *
 */