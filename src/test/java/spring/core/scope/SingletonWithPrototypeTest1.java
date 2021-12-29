package spring.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SingletonWithPrototypeTest1
{
    @Test
    void prototypeFind(){
        AnnotationConfigApplicationContext ac=new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);

    }

    @Test
    void singletonClientUsePrototype(){
        AnnotationConfigApplicationContext ac=new AnnotationConfigApplicationContext(ClientBean.class,PrototypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(2);

    }

    @Scope("singleton")
    static class ClientBean{
        private final PrototypeBean prototypeBean; //생성시점에 주입된다. 주입된것은 변하지 않는다.

        @Autowired
        public ClientBean(PrototypeBean prototypeBean) { //프로토타입 빈을 주입받는 싱글톤 빈
            this.prototypeBean=prototypeBean;
            //스프링 컨테이너가 프로토타입 빈을 만들어서 넣어준다. 이 할당된 프로토타입 빈이 계속 필드 값에 있음. 정확히는 참조
        }

        public int logic() {
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }

    @Scope("prototype") //프로토타입 사용
    static class PrototypeBean{
        private int count=0;

        public void addCount(){
            count++;
        }

        public int getCount(){
            return count;
        }

        @PostConstruct
        public void init(){
            System.out.println("PrototypeBean.init "+this);
        }

        @PreDestroy
        public  void destroy(){
            System.out.println("PrototypeBean.destroy");
        }
    }
}

/**
 * clientBean(싱글톤 빈)이 내부에 가지고 있는 프로토타입 빈은 이미 과거에 주입이 끝난 빈이다.
 * 주입 시점에 스프링 컨테이너에 요청해서 프로토타입 빈이 새로 생성이 된 것이지, 사용 할 때마다 새로 생성되는 것이 아니다!
 *
 * 스프링은 일반적으로 싱글톤 빈을 사용하므로, 싱글톤 빈이 프로토타입 빈을 사용하게 된다. 그런데 싱글톤
 * 빈은 생성 시점에만 의존관계 주입을 받기 때문에, 프로토타입 빈이 새로 생성되기는 하지만, 싱글톤 빈과 함께 계속 유지되는 것이 문제다.
 * 아마 원하는 것이 이런 것은 아닐 것이다. 프로토타입 빈을 주입 시점에만 새로 생성하는게 아니라, 사용할
 * 때 마다 새로 생성해서 사용하는 것을 원할 것이다.
 */

///만약 우리의 의도가 로직을 부를 때마다 프로토타입을 새로 만들어서 쓰고 싶다면?