package tobyspring.myboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tobyspring.myboot.hello.HelloRepository;
import tobyspring.myboot.hello.HelloService;

import java.util.stream.IntStream;

@HelloBootTest
public class HelloServiceCountTest {

    @Autowired
    HelloService helloService;
    @Autowired
    HelloRepository helloRepository;

    @Test
    void sayHelloIncreaseCount() {
        IntStream.rangeClosed(1, 10).forEach(count -> {
            helloService.sayHello("Toby");
            Assertions.assertThat(helloRepository.countOf("Toby")).isEqualTo(count);
        });
    }
}
