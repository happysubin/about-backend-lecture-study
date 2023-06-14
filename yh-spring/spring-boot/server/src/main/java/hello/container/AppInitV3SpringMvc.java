package hello.container;

import hello.spring.HelloConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class AppInitV3SpringMvc implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {

        System.out.println("AppInitV3SpringMvc.onStartup");

        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.register(HelloConfig.class);
        DispatcherServlet dispatcher = new DispatcherServlet(appContext);
        ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcherV3", dispatcher);
        servlet.addMapping("/");
    }
}
