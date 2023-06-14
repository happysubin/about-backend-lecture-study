package hello.container;

import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HandlesTypes;

import java.util.Set;

/**
 * @HandlesTypes 애노테이션에 애플리케이션 초기화 인터페이스를 지정한다.
 * 여기서는 앞서 만든 AppInit.class 인터페이스를 지정했다
 */

@HandlesTypes(AppInit.class)
public class MyContainerInitV2 implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx){

        System.out.println("MyContainerInitV2.onStartup");
        System.out.println("MyContainerInitV2 c = " + c);
        System.out.println("MyContainerInitV2 container = " + ctx);

        for (Class<?> appInitClass : c) {
            try{
                AppInit appInit = (AppInit) appInitClass.getDeclaredConstructor().newInstance();
                appInit.onStartup(ctx);
            }
            catch (Exception e){
                throw new RuntimeException(e);
            }
        }
    }
}
