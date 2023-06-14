//package tobyspring.myboot.hello;
//
//import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
//import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration(proxyBeanMethods = false)
//public class WebServerConfiguration {
//
//    /**
//     * Selector에 의해서 자동 구성되는 빈들은 사용자 구성이 마무리되어야 시작함.
//     * 고로 커스텀 웹 서버가 톰캣 웹 서버보다 먼저 빈으로 초기화된다.
//     * 자동 구성 정보보다는 사용자 구성정보가 우선된다
//     */
//    @Bean
//    public ServletWebServerFactory customWebServerFactory() {
//        TomcatServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
//        serverFactory.setPort(9090);
//        return serverFactory;
//    }
//}
