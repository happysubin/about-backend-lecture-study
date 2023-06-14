package spring.core.scan.filter;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.util.Assert;

public class ComponentFilterAppConfigTest {

    @Test
    void filterScan(){
        ApplicationContext ac=new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);
        BeanA beanA=ac.getBean("beanA",BeanA.class);
        Assertions.assertThat(beanA).isNotNull();

        //BeanB beanB=ac.getBean("beanB",BeanB.class); 오류 나옴 스캔 안됨
        org.junit.jupiter.api.Assertions.assertThrows(NoSuchBeanDefinitionException.class,()->
                ac.getBean("beanB",BeanB.class));
    }

    @Configuration
    @ComponentScan(
            includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes =
                    MyIncludeComponent.class), //컴포넌트 스캔 대상을 추가
            excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes =
                    MyExcludeComponent.class)  // 컴포넌트 스캔 대상 제거
    )
    static class ComponentFilterAppConfig {
    }
}

/**
 *
 * includeFilters 에 MyIncludeComponent 애노테이션을 추가해서 BeanA가 스프링 빈에 등록된다.
 * excludeFilters 에 MyExcludeComponent 애노테이션을 추가해서 BeanB는 스프링 빈에 등록되지
 * 않는다.
 */