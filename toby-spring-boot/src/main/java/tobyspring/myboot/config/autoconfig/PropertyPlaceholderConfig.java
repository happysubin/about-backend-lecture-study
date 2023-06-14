package tobyspring.myboot.config.autoconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import tobyspring.myboot.config.MyAutoConfiguration;

@MyAutoConfiguration
public class PropertyPlaceholderConfig {

    @Bean
    PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
        return new PropertySourcesPlaceholderConfigurer();
    }
}

/**
 * @Value의 치환자를 프로퍼티 값으로 교체하려면 PropertySourcesPlaceholderConfigurer의 빈을 등록해야한다.
 * 이는 빈 팩토리 후처리기로 동작해서 초기 구성 정보에서 치환자를 찾아서 교체하는 기능을 담당한다.
 */