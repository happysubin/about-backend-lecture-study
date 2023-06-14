package spring.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

public class StatefulServiceTest {

    @Test
    void statefulServiceSingleton(){
        ApplicationContext ac=new AnnotationConfigApplicationContext(TestConfig.class);

        StatefulService statefulService1=ac.getBean(StatefulService.class);
        StatefulService statefulService2=ac.getBean(StatefulService.class);

        //쓰레드 A : 사용자 A가 10000원을 주문
        statefulService1.order("userA",10000);

        //쓰레드 B : 사용자 B가 10000원을 주문 . 이 고객이 끼어들었다고 생각
        statefulService2.order("userB",20000);

        //쓰레드 A : 사용자 A가 주문 금액 조회
        int price=statefulService1.getPrice();

        System.out.println("price = " + price);
        Assertions.assertThat(price).isEqualTo(20000);
    }

    static class TestConfig{
        @Bean
        public StatefulService statefulService(){
            return new StatefulService();
        }
    }
}
