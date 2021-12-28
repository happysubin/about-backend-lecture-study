package spring.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {


    @Test void lifeCycleTest(){
        ConfigurableApplicationContext ac=new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();
    }

    @Configuration
    static class LifeCycleConfig{

        //설정 정보에 @Bean(initMethod = "init", destroyMethod = "close") 처럼 초기화, 소멸 메서드를 지정할 수 있다
        //@Bean(initMethod = "init", destroyMethod = "close")
        @Bean
        public NetworkClient networkClient(){
            //객체를 생성하고 이후에 값을 넣는 경우
            NetworkClient networkClient=new NetworkClient();
            networkClient.setUrl("https://hello.spring.dev");
            return networkClient;
        }
    }
}

/**
 *
 * 스프링 빈은 간단하게 다음과 같은 라이프사이클을 가진다.
 * 객체 생성 -> 의존관계 주입. (생성자 주입은 예외. 왜냐면 생성할 때 DI가 발생하므로 거의 동시에 진행된다고 이해)
 *
 * 스프링 빈은 객체를 생성하고, 의존관계 주입이 다 끝난 다음에야 필요한 데이터를 사용할 수 있는 준비가
 * 완료된다. 따라서 초기화 작업은 의존관계 주입이 모두 완료되고 난 다음에 호출해야 한다. 그런데 개발자가
 * 의존관계 주입이 모두 완료된 시점을 어떻게 알 수 있을까?
 * 스프링은 의존관계 주입이 완료되면 스프링 빈에게 콜백 메서드를 통해서 초기화 시점을 알려주는 다양한
 * 기능을 제공한다. 또한 스프링은 스프링 컨테이너가 종료되기 직전에 소멸 콜백을 준다. 따라서 안전하게
 * 종료 작업을 진행할 수 있다.
 * 
 * 스프링 빈의 이벤트 라이프사이클
 * 스프링 컨테이너 생성 ->스프링 빈 생성 -> 의존관계 주입 -> 초기화 콜백 사용 -> 소멸전 콜백 ->스프링 종료
 *
 *> 참고: 객체의 생성과 초기화를 분리하자.
 * > 생성자는 필수 정보(파라미터)를 받고, 메모리를 할당해서 객체를 생성하는 책임을 가진다. 반면에 초기화는
 * 이렇게 생성된 값들을 활용해서 외부 커넥션을 연결하는등 무거운 동작을 수행한다.
 * > 따라서 생성자 안에서 무거운 초기화 작업을 함께 하는 것 보다는 객체를 생성하는 부분과 초기화 하는
 * 부분을 명확하게 나누는 것이 유지보수 관점에서 좋다. 물론 초기화 작업이 내부 값들만 약간 변경하는
 * 정도로 단순한 경우에는 생성자에서 한번에 다 처리하는게 더 나을 수 있다.
 *
 * > 참고: 싱글톤 빈들은 스프링 컨테이너가 종료될 때 싱글톤 빈들도 함께 종료되기 때문에 스프링 컨테이너가
 * 종료되기 직전에 소멸전 콜백이 일어난다. 뒤에서 설명하겠지만 싱글톤 처럼 컨테이너의 시작과 종료까지
 * 생존하는 빈도 있지만, 생명주기가 짧은 빈들도 있는데 이 빈들은 컨테이너와 무관하게 해당 빈이 종료되기
 * 직전에 소멸전 콜백이 일어난다.
 */