package spring.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

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
        assertThat(count2).isEqualTo(1);

    }

    @Scope("singleton")
    static class ClientBean{

        @Autowired //ObjectProvider 이 ObjectFactory를 상속받음. 그래서 Provider을 쓰자.
        private ObjectProvider<PrototypeBean> prototypeBeanProvider;
        //스프링 빈에 등록한적없지만 스프링이 알아서 넣어준다.
        //provider의 기능 : getObject를 하면 얘가 스프링 컨테이너에서 프로토타입 빈을 찾아서 반환.

        public int logic() { //다른 객체 출력을 확인 가능
            PrototypeBean prototypeBean=prototypeBeanProvider.getObject();
            /**실행해보면 prototypeBeanProvider.getObject() 을 통해서 항상 새로운 프로토타입 빈이 생성되는 것을 확인할 수 있다.
             ObjectProvider 의 getObject() 를 호출하면 내부에서는 스프링 컨테이너를 통해 해당 빈을 찾아서 반환한다. (DL)
             스프링이 제공하는 기능을 사용하지만, 기능이 단순하므로 단위테스트를 만들거나 mock 코드를 만들기 훨씬 쉬워진다.
             프로토타입이니 스프링 컨테이너에서 찾는 게아니라 스프링 컨테이너가 만들어서 제공해준다.
             */
            prototypeBean.addCount();
            int count= prototypeBean.getCount();
            return count;
        }
        @Autowired
        private Provider<PrototypeBean> provider;
        public int logic2()
        {
            PrototypeBean prototypeBean = provider.get();
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }

        /**
         * 실행해보면 provider.get() 을 통해서 항상 새로운 프로토타입 빈이 생성되는 것을 확인할 수 있다.
         * provider 의 get() 을 호출하면 내부에서는 스프링 컨테이너를 통해 해당 빈을 찾아서 반환한다. (DL)
         * 자바 표준이고, 기능이 단순하므로 단위테스트를 만들거나 mock 코드를 만들기는 훨씬 쉬워진다.
         * Provider 는 지금 딱 필요한 DL 정도의 기능만 제공한다.
         * 특징
         * get() 메서드 하나로 기능이 매우 단순하다.
         * 별도의 라이브러리가 필요하다.
         * 자바 표준이므로 스프링이 아닌 다른 컨테이너에서도 사용할 수 있다.
         */


        /** 긴딘힌 해결법
         *
         * @Autowired private ApplicationContext ac;
        public int logic() {
        PrototypeBean prototypeBean = ac.getBean(PrototypeBean.class);
        prototypeBean.addCount();
        int count = prototypeBean.getCount();
        return count;
        }
         * 가장 간단한 방법은 싱글톤 빈이 프로토타입을 사용할 때 마다 스프링 컨테이너에 새로 요청하는 것이다.
         *
         * 프로토타입 스코프 빈에 대해서는 getBean 시 내부적으로 빈을 새로 생성하고 그 빈을 반환한다.
         * getBean 이 스프링컨테이너에 등록된 빈을 가져온다는 생각은 싱글톤 빈에 대한 것이다.
         *
         * 실행해보면 ac.getBean() 을 통해서 항상 새로운 프로토타입 빈이 생성되는 것을 확인할 수 있다.
         * 의존관계를 외부에서 주입(DI) 받는게 아니라 이렇게 직접 필요한 의존관계를 찾는 것을 Dependency
         * Lookup (DL) 의존관계 조회(탐색) 이라한다.
         * 그런데 이렇게 스프링의 애플리케이션 컨텍스트 전체를 주입받게 되면, 스프링 컨테이너에 종속적인 코드가
         * 되고, 단위 테스트도 어려워진다.
         * 지금 필요한 기능은 지정한 프로토타입 빈을 컨테이너에서 대신 찾아주는 딱! DL 정도의 기능만 제공하는
         * 무언가가 있으면 된다
         *
         */
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

