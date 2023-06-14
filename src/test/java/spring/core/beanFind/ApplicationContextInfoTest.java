package spring.core.beanFind;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.core.AppConfig;

public class ApplicationContextInfoTest {
    AnnotationConfigApplicationContext ac=new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("모든 빈 출력하기")
    public void findAllBean(){ //사실 junit 5부터는 public 안 써도 된다.
        String[] beanDefinitionNames=ac.getBeanDefinitionNames();
        for(String beanDefinitionName:beanDefinitionNames){
            Object bean= ac.getBean(beanDefinitionName);
            System.out.println("name = "+beanDefinitionName+" object = "+bean );
        }
    }

    //스프링 내부에 기본적인 설정 빈들도 보인다.

    @Test
    @DisplayName("애플리케이션 빈 출력하기")
    public void findApplicationBeans(){ //사실 junit 5부터는 public 안 써도 된다.
        String[] beanDefinitionNames=ac.getBeanDefinitionNames();
        for(String beanDefinitionName:beanDefinitionNames){
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);


            //Role ROLE_INFRASTRUCTURE: 스프링이 내부에서 사용하는 빈 이걸로 내부 검사 가능
            if(beanDefinition.getRole()==BeanDefinition.ROLE_APPLICATION){ //우리가 애플리케이션 개발을 위해 등록한 빈인지 검사
                Object bean= ac.getBean(beanDefinitionName);
                System.out.println("name = "+beanDefinitionName+" object = "+bean );
            }
        }
    }
    //우리가 등록한 빈들만 확인 가능
}

