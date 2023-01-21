package tobyspring.myboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import tobyspring.myboot.hello.HelloDecorator;
import tobyspring.myboot.hello.SimpleHelloService;

public class HelloServiceTest {

    @Test
    void simpleHelloService(){

        SimpleHelloService simpleHelloService = new SimpleHelloService();

        String result = simpleHelloService.sayHello("Test");

        Assertions.assertThat(result).isEqualTo("Hello Test");
    }

    @Test
    void helloDecorator(){

        HelloDecorator helloDecorator = new HelloDecorator(name -> name);

        String result = helloDecorator.sayHello("Test");

        Assertions.assertThat(result).isEqualTo("* Test *");
    }
}
