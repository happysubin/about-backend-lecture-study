//package tobyspring.myboot.config.autoconfig;
//
//import org.springframework.boot.context.properties.bind.Binder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.env.Environment;
//import tobyspring.myboot.config.MyAutoConfiguration;
//
//@MyAutoConfiguration
//public class ServerPropertiesConfig {
//
//    @Bean
//    public ServerProperties serverProperties(Environment environment){
//        return Binder.get(environment).bind("",ServerProperties.class).get();
//    }
//}
//
///**
// * Environment에서 프로퍼티 값을 가져와 객체에 주입하는 것은 스프링 부트의 Binder 클래스를 이용하면 편리.
// */