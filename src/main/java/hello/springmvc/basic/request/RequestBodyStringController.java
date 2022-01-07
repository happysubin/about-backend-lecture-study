package hello.springmvc.basic.request;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

/**
 * 요청 파라미터와 다르게, HTTP 메시지 바디를 통해 데이터가 직접 데이터가 넘어오는 경우는
 * @RequestParam , @ModelAttribute 를 사용할 수 없다.
 * (물론 HTML Form 형식으로 전달되는 경우는 요청 파라미터로 인정된다.)
 */

@Slf4j
@Controller
public class RequestBodyStringController {

    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest req, HttpServletResponse res)throws IOException {
        InputStream inputStream=req.getInputStream();
        String messageBody=StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        //항상 바이트를 문자로 바꾸면 인코딩을 지정

        log.info("messageBody={}", messageBody);
        res.getWriter().write("ok");

    }

    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter)throws IOException {
        //서블릿 코드를 지우고 스트림을 바로 받고 writer를 받을 수 있다.
        String messageBody=StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        //항상 바이트를 문자로 바꾸면 인코딩을 지정

        log.info("messageBody={}", messageBody);
        responseWriter.write("ok");

        /**
         * 스프링 MVC는 다음 파라미터를 지원한다.
         * InputStream(Reader): HTTP 요청 메시지 바디의 내용을 직접 조회
         * OutputStream(Writer): HTTP 응답 메시지의 바디에 직접 결과 출력
         */
    }

    /**
     * HttpEntity: HTTP header, body 정보를 편라하게 조회
     * - 메시지 바디 정보를 직접 조회(@RequestParam X, @ModelAttribute X)
     * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     *
     * 응답에서도 HttpEntity 사용 가능
     * - 메시지 바디 정보 직접 반환(view 조회X)
     * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     */

    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity){//http 바디의 것을 문자열로 바꿔서 객체에 넣는다
        String body=httpEntity.getBody();
        log.info("messageBody={}",body);

        return new HttpEntity<>("ok");
    }
    /**
     * 스프링 MVC는 다음 파라미터를 지원한다.
     * HttpEntity: HTTP header, body 정보를 편리하게 조회
     * 메시지 바디 정보를 직접 조회
     * 요청 파라미터를 조회하는 기능과 관계 없음 @RequestParam X, @ModelAttribute X
     *  위 경우 제외하면 전부다 HttpEntity 기능 사용
     *
     * HttpEntity는 응답에도 사용 가능
     * 메시지 바디 정보 직접 반환
     * 헤더 정보 포함 가능
     * view 조회X
     *
     * HttpEntity 를 상속받은 다음 객체들도 같은 기능을 제공한다.
     * RequestEntity
     * HttpMethod, url 정보가 추가, 요청에서 사용
     * ResponseEntity
     * HTTP 상태 코드 설정 가능, 응답에서 사용
     * return new ResponseEntity<String>("Hello World", responseHeaders,
     * HttpStatus.CREATED)
     */


    /**
     * @RequestBody
     * - 메시지 바디 정보를 직접 조회(@RequestParam X, @ModelAttribute X)
     * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     *
     * @ResponseBody
     * - 메시지 바디 정보 직접 반환(view 조회X)
     * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     */
    @ResponseBody
    @PostMapping("/request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String messageBody) {
        log.info("messageBody={}", messageBody);
        return "ok";
    }
}

