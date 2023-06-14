package hello.container;

import hello.spring.HelloConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRegistration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class AppInitV2Spring implements AppInit{

    @Override
    public void onStartup(ServletContext servletContext) {
        System.out.println("AppInitV2Spring.onStartup");

        AnnotationConfigWebApplicationContext ac = new AnnotationConfigWebApplicationContext();
        ac.register(HelloConfig.class);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(ac);

        ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcherV2", dispatcherServlet);
        servlet.addMapping("/spring/*");
    }
}
