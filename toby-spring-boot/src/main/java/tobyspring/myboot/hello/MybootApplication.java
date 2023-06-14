//package tobyspring.myboot;
//
//import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
//
//import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.DispatcherServlet;
//
//@Configuration
//@ComponentScan
//public class MybootApplication {
//
//	@Bean
//	public ServletWebServerFactory servletWebServerFactory() {
//		return new TomcatServletWebServerFactory();
//	}
//
//	@Bean
//	public DispatcherServlet dispatcherServlet() {
//		return new DispatcherServlet();
//	}
//
//	/**
//	 * 스프링 부트 애플리케이션과 똑같은 모습이 되었다. 대박.
//	 */
//	public static void main(String[] args) {
//		MySpringApplication.run(MybootApplication.class, args);
//	}
//}
