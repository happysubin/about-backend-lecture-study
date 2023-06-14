package spring.core.scan.filter;


import java.lang.annotation.*;

@Target(ElementType.TYPE) //이게 중요
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyIncludeComponent {
}
