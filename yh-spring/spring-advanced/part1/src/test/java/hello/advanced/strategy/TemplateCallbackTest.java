package hello.advanced.strategy;

import hello.advanced.strategy.code.strategy.ContextV1;
import hello.advanced.strategy.code.strategy.Strategy;
import hello.advanced.strategy.code.strategy.StrategyLogic1;
import hello.advanced.strategy.code.strategy.StrategyLogic2;
import hello.advanced.strategy.code.template.Callback;
import hello.advanced.strategy.code.template.TimeLogTemplate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class TemplateCallbackTest {
    /**
     *템플릿 콜백 패턴-익명 내부 클래스
     */
    @Test
    void callbackV1() {
        TimeLogTemplate template = new TimeLogTemplate();
        template.execute(new Callback() {
            @Override
            public void call() {
                log.info("비즈니스 로직1 실행");
            }
        });

        template.execute(new Callback() {
            @Override
            public void call() {
                log.info("비즈니스 로직2 실행");
            }
        });
    }

    //람다
    @Test
    void callbackV2() {
        TimeLogTemplate template = new TimeLogTemplate();
        template.execute(() -> log.info("비즈니스 로직1 실행"));
        template.execute(() -> log.info("비즈니스 로직2 실행"));
    }
}