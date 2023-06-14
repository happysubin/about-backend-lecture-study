package hello.advanced.strategy;

import hello.advanced.strategy.code.strategy.ContextV1;
import hello.advanced.strategy.code.strategy.Strategy;
import hello.advanced.strategy.code.strategy.StrategyLogic1;
import hello.advanced.strategy.code.strategy.StrategyLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV1Test {

    @Test
    void strategyV0(){
        logic1();
        logic2();
    }

    private void logic2() {
        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        log.info("비즈니스 로직2 실행");
        //비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }

    private void logic1(){
        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        log.info("비즈니스 로직1 실행");
        //비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }


    /**
     *
     * 전략 패턴 적용
     *
     */

    @Test
    void strategyV1(){
        Strategy StrategyLogic1 = new StrategyLogic1();
        ContextV1 context1 = new ContextV1(StrategyLogic1);
        context1.execute();

        Strategy strategyLogic2 = new StrategyLogic2();
        ContextV1 context2 = new ContextV1(strategyLogic2);
        context2.execute();
    }


    /**
     * 전략 패턴 익명 내부 클래스 사용
     *
     */
    @Test
    void strategyV2(){
         Strategy strategyLogic1 = new StrategyLogic1(){
             @Override
             public void call() {
                 log.info("비즈니스 로직1 실행");
             }
         };
         log.info("strategyLogic1={}", strategyLogic1.getClass());
         ContextV1 context1 = new ContextV1(strategyLogic1);
         context1.execute();

         Strategy strategyLogic2 = new StrategyLogic1(){
             @Override
             public void call() {
                 log.info("비즈니스 로직2 실행");
             }
         };

         log.info("strategyLogic2={}", strategyLogic2.getClass());
         ContextV1 context2 = new ContextV1(strategyLogic2);
         context2.execute();

     }

    @Test
    void strategyV3(){

        ContextV1 context1 = new ContextV1(new StrategyLogic1(){
            @Override
            public void call() {
                log.info("비즈니스 로직1 실행");
            }
        });
        context1.execute();

        ContextV1 context2 = new ContextV1(new StrategyLogic1(){
            @Override
            public void call() {
                log.info("비즈니스 로직2 실행");
            }
        });
        context2.execute();
    }

    @Test
    void strategyV4(){
        ContextV1 context1 = new ContextV1(() -> log.info("비즈니스 로직1 실행"));
        context1.execute();

        ContextV1 context2 = new ContextV1(() -> log.info("비즈니스 로직2 실행"));
        context2.execute();
    }
    /**
     * 선 조립 후 실행.
     * Context 와 Strategy 를 한번 조립하고 나면 이후로는 Context 를 실행하기만 하면 된다.
     * 스프링에서 의존 관계를 주입하고 요청을 받는 관계와 동일하다.
     * 이 방식의 단점은 Context 와 Strategy 를 조립한 이후에는 전략을 변경하기가 번거롭다는 점이다.
     * 다른 방법은?
     */
}