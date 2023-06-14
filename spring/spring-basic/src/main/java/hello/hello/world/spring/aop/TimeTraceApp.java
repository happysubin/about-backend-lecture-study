package hello.hello.world.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component //스프링 빈으로 등록
public class TimeTraceApp {
    @Around("execution(* hello.hello.world.spring..*(..))") //패키지명을 적고 하위에 있는 모든것을 적용
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{
        long start=System.currentTimeMillis();

        System.out.println("START : "+joinPoint.toString());

        try{
            return joinPoint.proceed();
        }
        finally {
            long finish=System.currentTimeMillis();
            long timeMs=finish-start;
            System.out.println("END: " + joinPoint.toString()+ " " + timeMs + "ms");
        }
    }
}
