package hello.container;

import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;

import java.util.Set;

public class MyContainerInitV1 implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx){
        System.out.println("hello world:");
        System.out.println("c = " + c);
        System.out.println("c = " + ctx);
    }
}
