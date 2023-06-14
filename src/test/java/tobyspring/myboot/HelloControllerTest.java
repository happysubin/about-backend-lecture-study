package tobyspring.myboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import tobyspring.myboot.hello.HelloController;


public class HelloControllerTest {

    @Test
    void helloControllerTest(){
        HelloController helloController = new HelloController(name -> name); //이것도 테스트를 위한 일종의 DI
        String ret = helloController.hello("Test");
        Assertions.assertThat(ret).isEqualTo("Test");
    }

    @Test
    void exception(){
        HelloController helloController = new HelloController(name -> name);
        Assertions.assertThatThrownBy(() -> helloController.hello(null)).isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatThrownBy(() -> helloController.hello("")).isInstanceOf(IllegalArgumentException.class);
    }
}
