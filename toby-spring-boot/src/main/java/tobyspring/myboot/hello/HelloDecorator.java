package tobyspring.myboot.hello;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary //스프링 컨테이너가 의존관계 주입시 인터페이스의 여러 구현체 중 제일 우선시 된다.
@Component
public class HelloDecorator implements HelloService{

    private final HelloService helloService;

    public HelloDecorator(HelloService helloService) {
        this.helloService = helloService;
    }

    @Override
    public String sayHello(String name) {
        return "* " + helloService.sayHello(name) + " *";
    }

    @Override
    public int countOf(String name) {
        return helloService.countOf(name);
    }
}