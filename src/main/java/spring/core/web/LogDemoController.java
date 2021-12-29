package spring.core.web;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import spring.core.common.MyLogger;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor //롬복 라이브러리에서 지원하는 애노테이션. final이 붙은 필드를 모아서 생성자를 자동으로 만들어준다.
public class LogDemoController {
    private final LogDemoService logDemoService;
    private final ObjectProvider<MyLogger> myLoggerProvider;

    @RequestMapping("log-demo")
    @ResponseBody //문자를 바로 반환
    public String logDemo(HttpServletRequest request){ //http 요청 정보를 받는다.
        String reqURL=request.getRequestURL().toString();
        MyLogger myLogger=myLoggerProvider.getObject();
        myLogger.setRequestURL(reqURL);
        myLogger.log("controller test");

        logDemoService.logic("testId");
        return "OK";
    }
}

/**
 * 로거가 잘 작동하는지 확인하는 테스트용 컨트롤러다.
 * 여기서 HttpServletRequest를 통해서 요청 URL을 받았다.
 * requestURL 값 http://localhost:8080/log-demo
 * 이렇게 받은 requestURL 값을 myLogger에 저장해둔다. myLogger는 HTTP 요청 당 각각 구분되므로
 * 다른 HTTP 요청 때문에 값이 섞이는 걱정은 하지 않아도 된다.
 * 컨트롤러에서 controller test라는 로그를 남긴다.
 */


/**
 * ObjectProvider 덕분에 ObjectProvider.getObject() 를 호출하는 시점까지 request scope 빈의
 * 생성을 지연할 수 있다.
 * ObjectProvider.getObject() 를 호출하시는 시점에는 HTTP 요청이 진행중이므로 request scope
 * 빈의 생성이 정상 처리된다.
 * ObjectProvider.getObject() 를 LogDemoController , LogDemoService 에서 각각 한번씩 따로
 * 호출해도 같은 HTTP 요청이면 같은 스프링 빈이 반환된다! 내가 직접 이걸 구분하려면 얼마나 힘들까..
 */