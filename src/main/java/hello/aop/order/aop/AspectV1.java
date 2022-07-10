package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class AspectV1 {

    @Around("execution(* hello.aop.order..*(..))") //포인트컷
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable{ //이 메서드가 어드바이
        log.info("[log] {}", joinPoint.getSignature()); //join point 시그니처
        return joinPoint.proceed();
    }
}

/**
 * 1. @Aspect가 어드바이저로 변환된다.
 * 2. @Aspect 어드바이저 빌더를 통해 @Aspect 애노테이션 정보를 기반으로 어드바이저를 생성한다.
 * 3. 스프링 AOP는 스프링 컨테이너가 관리할 수 있는 스프링 빈에만 AOP를 적용할 수 있다. 어드바이저를 따라서 빈으로 등록해야 한다. @Aspect 가 붙은 클래스를 빈으로 등록.
 * 4. 스프링 부트 자동 설정으로 AnnotationAwareAspectJAutoProxyCreator 라는 빈 후처리기가 스프링 빈에 자동으로 등록된다.
 * 5. 이 빈 후처리기는 스프링 빈으로 등록된 Advisor 들을 자동으로 찾아서 프록시가 필요한 곳에 자동으로 프록시를 적용해준다.
 * 6. 그럼 AOP 적용이 완료된다.
 *
 *
 *
 *
 * /
