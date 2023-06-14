package tobyspring.myboot.hello;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME) //이 애노테이션이 언제까지 살아있을것인가/
@Target(ElementType.TYPE) //애노테이션이 들어갈 대상을 지정
@Component
public @interface MyComponent {
}

/**
 * @Target(ElementType.TYPE) //ElementType.ANNOTATION_TYPE 이여야만 메타 애노테이션으로 사용할 수 있다.
 * @Retention(RetentionPolicy.RUNTIME)
 * @Documented
 * @Component    // Meta Annotation
 * public @interface Service {
 * }
 * 메타 애노테이션으로 컴포넌트 등록. 메타 에노테이션이란 애노테이션 위에 붙어 있는 애노테이션
 * 상속의 개념은 아님.
 *
 *
 * 합성 애노테이션은 하나 이상의 메타 애노테이션이 적용된 애노테이션이다.
 */