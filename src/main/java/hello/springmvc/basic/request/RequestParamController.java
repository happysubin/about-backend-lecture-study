package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    /**
     * 반환 타입이 없으면서 이렇게 응답에 값을 직접 집어넣으면, view 조회X
     */
    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={}, age={}", username, age);
        response.getWriter().write("ok");
    }


    /**
     * @RequestParam 사용
     * - 파라미터 이름으로 바인딩
     * @ResponseBody 추가
     * - View 조회를 무시하고, HTTP message body에 직접 해당 내용 입력
     */
    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memberName,
            @RequestParam("age") int memberAge
    ){
        log.info("username={}, age={}",memberName,memberAge);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            @RequestParam String username, //변수명이 같으면 생략가능 V2와 차이점
            @RequestParam int age
    ){
        log.info("username={}, age={}",username,age);
        return "ok";
    }

    /**
     * @RequestParam 사용
     * String, int 등의 단순 타입이면 @RequestParam 도 생략 가능
     */
    @ResponseBody
    @RequestMapping("request-param-v4")
    public String requestParamV4(String username, int age){//요청 파라미터 이름과 같다면 성공적
        log.info("username={}, age={}",username,age);
        return "ok";
        //String , int , Integer 등의 단순 타입이면 @RequestParam 도 생략 가능
    }

    @ResponseBody
    @RequestMapping("request-param-required")// 값이 꼭 들어와야하는지 아닌지 설정
    public String requestParamRequired(
        @RequestParam(required = false) String username,//아무것도 안적으면 true 즉 꼭들어와야한다.
        @RequestParam(required = true) Integer age
    ){
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * @RequestParam
     * - defaultValue 사용
     *
     * 참고: defaultValue는 빈 문자의 경우에도 적용
     * /request-param?username=
     */
    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            @RequestParam(required = true, defaultValue = "guest") String username, //값이 안들어오면 이걸로
            @RequestParam(required = false, defaultValue = "-1") int age) { //값이 없으면 이걸로
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * @RequestParam Map, MultiValueMap
     * Map(key=value)
     * MultiValueMap(key=[value1, value2, ...] ex) (key=userIds, value=[id1, id2])
     */
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
        log.info("username={}, age={}", paramMap.get("username"),
                paramMap.get("age"));
        return "ok";
    }
    /**@RequestParam Map ,
    Map(key=value)
     @RequestParam MultiValueMap
     MultiValueMap(key=[value1, value2, ...] ex) (key=userIds, value=[id1, id2])
     파라미터의 값이 1개가 확실하다면 Map 을 사용해도 되지만, 그렇지 않다면 MultiValueMap 을 사용하자.
     *
     */


   /* @ModelAttribute 사용
    * 참고: model.addAttribute(helloData) 코드도 함께 자동 적용됨
    */
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData){
        log.info("username={},age={}",helloData.getUsername(),helloData.getAge());
        log.info("helloData={}",helloData);
        return "ok";
    }
    //modelAttribute를 사용하면 객체도 생성하고, 요청 파라미터의 값도 모두 들어가있다.
    /**
     * HelloData 객체를 생성한다.
     * 요청 파라미터의 이름으로 HelloData 객체의 프로퍼티를 찾는다. 그리고 해당 프로퍼티의 setter를
     * 호출해서 파라미터의 값을 입력(바인딩) 한다. username과 age
     * 예) 파라미터 이름이 username 이면 setUsername() 메서드를 찾아서 호출하면서 값을 입력한다.
     */

    /**
     * @ModelAttribute 생략 가능 (짱이네...)
     * String, int 같은 단순 타입 = @RequestParam
     * argument resolver 로 지정해둔 타입 외 = @ModelAttribute
     */

    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData){
        log.info("username={}, age={}", helloData.getUsername(),
                helloData.getAge());
        return "ok";
        /**
         * @ModelAttribute 는 생략할 수 있다.
         * 그런데 @RequestParam 도 생략할 수 있으니 혼란이 발생할 수 있다.
         * 스프링은 해당 생략시 다음과 같은 규칙을 적용한다.
         * String , int , Integer 같은 단순 타입 = @RequestParam
         * 나머지 = @ModelAttribute
         * (argument resolver 로 지정해둔 타입 외)
         */
    }
}
