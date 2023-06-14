package hello.exception;

import hello.exception.filter.LogFilter;
import hello.exception.interceptor.LogInterceptor;
import hello.exception.resolver.MyHandlerExceptionResolver;
import hello.exception.resolver.UserHandlerExceptionResolver;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.List;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**","*.ico","/error","/error-page/**");//오류 페이지 경로
    }
    //여기에서 /error-page/** 를 제거하면 error-page/500 같은 내부 호출의 경우에도 인터셉터가 호출된다.


    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        resolvers.add(new MyHandlerExceptionResolver()); //등록한다.
        resolvers.add(new UserHandlerExceptionResolver());
    }
    /**
     * ExceptionResolver 활용
     * 예외 상태 코드 변환
     * 예외를 response.sendError(xxx) 호출로 변경해서 서블릿에서 상태 코드에 따른 오류를
     * 처리하도록 위임
     * 이후 WAS는 서블릿 오류 페이지를 찾아서 내부 호출, 예를 들어서 스프링 부트가 기본으로 설정한 /
     * error 가 호출됨
     * 뷰 템플릿 처리
     * ModelAndView 에 값을 채워서 예외에 따른 새로운 오류 화면 뷰 렌더링 해서 고객에게 제공
     * API 응답 처리
     * response.getWriter().println("hello"); 처럼 HTTP 응답 바디에 직접 데이터를 넣어주는
     * 것도 가능하다. 여기에 JSON 으로 응답하면 API 응답 처리를 할 수 있다.
     * */

    //@Bean
    public FilterRegistrationBean logFilter(){
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new LogFilter());
        filterFilterRegistrationBean.setOrder(1);
        filterFilterRegistrationBean.addUrlPatterns("/*");
        filterFilterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST,DispatcherType.ERROR);
        //에러 요청인 경우에 호출된다.

        return filterFilterRegistrationBean;
    }
}


/**
 * /error-ex 오류 요청
 * 필터는 DispatchType 으로 중복 호출 제거 ( dispatchType=REQUEST )
 * 인터셉터는 경로 정보로 중복 호출 제거( excludePathPatterns("/error-page/**") )
 * 1. WAS(/error-ex, dispatchType=REQUEST) -> 필터 -> 서블릿 -> 인터셉터 -> 컨트롤러
 * 2. WAS(여기까지 전파) <- 필터 <- 서블릿 <- 인터셉터 <- 컨트롤러(예외발생)
 * 3. WAS 오류 페이지 확인
 * 4. WAS(/error-page/500, dispatchType=ERROR) -> 필터(x) -> 서블릿 -> 인터셉터(x) ->
 * 컨트롤러(/error-page/500) -> View
 *
 */