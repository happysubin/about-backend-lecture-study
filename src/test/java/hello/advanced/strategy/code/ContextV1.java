package hello.advanced.strategy.code;

import lombok.extern.slf4j.Slf4j;

//전략 패턴. 스프링에서 의존관계 주입에서 사용하는 방식이 바로 전략 패턴이다.

@Slf4j
public class ContextV1 { //템플릿 역할을 하는 코드. 전략 패턴에서는 이를 문맥, 즉 컨텍스트라고 부른다.

    private Strategy strategy;


    public ContextV1(Strategy strategy) {
        this.strategy = strategy;
    }

    public void execute(){
        long startTime = System.currentTimeMillis();

        //비즈니스 로직 실행
        strategy.call(); //위임
        //비즈니스 로직 종료

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }
}
