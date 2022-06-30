package hello.advanced.strategy.code;

import lombok.extern.slf4j.Slf4j;

//전략 패턴. 전략을 파라미터로 전달하는 방식

@Slf4j
public class ContextV2 { //템플릿 역할을 하는 코드. 전략 패턴에서는 이를 문맥, 즉 컨텍스트라고 부른다.

    public void execute(Strategy strategy){
        long startTime = System.currentTimeMillis();

        //비즈니스 로직 실행
        strategy.call(); //위임
        //비즈니스 로직 종료

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }
}
