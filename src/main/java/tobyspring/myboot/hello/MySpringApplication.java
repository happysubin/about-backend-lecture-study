package tobyspring.myboot.hello;

import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class MySpringApplication {

    public static void run(Class<?> applicationClass, String... args) {
        /**
         * 서블릿 컨테이너 작업 및 초기화를 스프링 컨테이너 초기화 과정중에 발생하도록 수정. 스프링 부트가 이런 방식.
         * AnnotationConfigWebApplicationContext 애노테이션 자바 구성을 인식할 수 있음.
         */

        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext() {
            @Override
            protected void onRefresh() {
                super.onRefresh();
                ServletWebServerFactory serverFactory = this.getBean(ServletWebServerFactory.class);

                /**
                 * ApplicationContextAware를 구현한 디스패처 서블릿.
                 * 이 인터페이스를 구현한다면 스프링 컨테이너가 세터 메서드를 사용해 ApplicationContext를 주입해줌.
                 */

                DispatcherServlet dispatcherServlet = this.getBean(DispatcherServlet.class);

                WebServer webServer = serverFactory.getWebServer(servletContext -> {
                    servletContext.addServlet("dispatcherServlet", dispatcherServlet)
                            .addMapping("/*");
                });
                webServer.start();
            }
        };

        applicationContext.register(applicationClass);
        applicationContext.refresh(); //구성 정보를 가지고 컨테이너를 초기화함. 빈 오브젝트를 다 만듦. 템플릿 메서드 방식임.
    }
}
