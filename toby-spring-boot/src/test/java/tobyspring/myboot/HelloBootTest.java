package tobyspring.myboot;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import tobyspring.myboot.hello.MybootApplicationV2;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MybootApplicationV2.class) //애플리케이션 스프링 설정을 가져옴
@TestPropertySource("classpath:/application.properties") //프로퍼티 소스를 가져옴.
@Transactional
public @interface HelloBootTest {
}
