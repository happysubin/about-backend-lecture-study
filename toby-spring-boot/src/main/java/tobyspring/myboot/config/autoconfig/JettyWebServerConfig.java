package tobyspring.myboot.config.autoconfig;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.type.AnnotatedTypeMetadata;
import tobyspring.myboot.config.ConditionalMyOnClass;
import tobyspring.myboot.config.MyAutoConfiguration;

@MyAutoConfiguration
@ConditionalMyOnClass("org.eclipse.jetty.server.Server")
public class JettyWebServerConfig {

    @Bean("jettyWebserverFactory")
    @ConditionalOnMissingBean
    public ServletWebServerFactory servletWebServerFactory() {
        return new JettyServletWebServerFactory();
    }
}

/**
 * @Conditional은 애노테이션으로 모든 조건을 만족하는 경우에만 컨테이너에 빈으로 등록되도록 한다.
 * Condition은 @Conditional에 지정되어서 구체적인 매칭 조건을 가진 클래스가 구현해야할 인터페이스다.
 * @Conditional은 @Configuration 클래스와 @Bean 메서드에 적용 가능. 클래스 조건을 만족하지 못하는 경우 메서드는 무시됨.
 */