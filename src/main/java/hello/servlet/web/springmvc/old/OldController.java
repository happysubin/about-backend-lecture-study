package hello.servlet.web.springmvc.old;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("/springmvc/old-controller")
public class OldController implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("OldController.handleRequest");
        return new ModelAndView("new-form");
    }
}

/**
 * 1. 핸들러 매핑으로 핸들러 조회
 1. HandlerMapping 을 순서대로 실행해서, 핸들러를 찾는다.
 2. 이 경우 빈 이름으로 핸들러를 찾아야 하기 때문에 이름 그대로 빈 이름으로 핸들러를 찾아주는
 BeanNameUrlHandlerMapping 가 실행에 성공하고 핸들러인 OldController 를 반환한다.


 2. 핸들러 어댑터 조회
 1. HandlerAdapter 의 supports() 를 순서대로 호출한다.
 2. SimpleControllerHandlerAdapter 가 Controller 인터페이스를 지원하므로 대상이 된다.


 3. 핸들러 어댑터 실행
 1. 디스패처 서블릿이 조회한 SimpleControllerHandlerAdapter 를 실행하면서 핸들러 정보도 함께
 넘겨준다.
 2. SimpleControllerHandlerAdapter 는 핸들러인 OldController 를 내부에서 실행하고, 그 결과를
 반환한다.
 정리 - OldController 핸들러매핑, 어댑터
 OldController 를 실행하면서 사용된 객체는 다음과 같다.
 HandlerMapping = BeanNameUrlHandlerMapping
 HandlerAdapter = SimpleControllerHandlerAdapte
 *
 */


/**뷰 리졸버
 *
 * 1. 핸들러 어댑터 호출
 * 핸들러 어댑터를 통해 new-form 이라는 논리 뷰 이름을 획득한다.
 *
 * 2. ViewResolver 호출
 * new-form 이라는 뷰 이름으로 viewResolver를 순서대로 호출한다.
 * BeanNameViewResolver 는 new-form 이라는 이름의 스프링 빈으로 등록된 뷰를 찾아야 하는데 없다.
 * InternalResourceViewResolver 가 호출된다.
 * 3. InternalResourceViewResolver
 * 이 뷰 리졸버는 InternalResourceView 를 반환한다.
 * 4. 뷰 - InternalResourceView
 * InternalResourceView 는 JSP처럼 포워드 forward() 를 호출해서 처리할 수 있는 경우에 사용한다.
 * 5. view.render()
 * view.render() 가 호출되고 InternalResourceView 는 forward() 를 사용해서 JSP를 실행한다
 */