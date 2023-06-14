package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Slf4j //로그 찍기 위함
@RestController
public class RequestHeaderController {


    @RequestMapping("/headers")
    public String headers(
            HttpServletRequest req,
            HttpServletResponse res,
            HttpMethod httpMethod,
            Locale locale, //까먹지 말자 언어 관련 헤더
            @RequestHeader MultiValueMap<String,String> headerMap, //헤더를 다 받는다. multiValuemMap는 한 키에 여러 값을 가질 수 있다.
            @RequestHeader("host") String host, //http 특정 헤더 조회
            @CookieValue(value = "MyCookie",required = false) String cookie //default는 true
            ){

        log.info("request={}", req);
        log.info("response={}", res);
        log.info("httpMethod={}", httpMethod);
        log.info("locale={}", locale);
        log.info("headerMap={}", headerMap);
        log.info("header host={}", host);
        log.info("myCookie={}", cookie);
        return "ok";
    }
}
