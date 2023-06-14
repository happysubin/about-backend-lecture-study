package hello.springmvc.basic;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//@Slf4j  private final Logger log= LoggerFactory.getLogger(getClass()); 이 코드를 안적어도됨
@RestController //Restapi에 그 Rest
public class LogTestController {
    private final Logger log= LoggerFactory.getLogger(getClass());

    @RequestMapping("/log-test")
    public String logTest(){
        String name="Spring";
        //System.out.println("name = " + name); //과거에 우리가 사용한 방식

        log.trace("trace log={}.{}",name);
        log.debug("debug log={}.{}",name);

        log.info("info log={}",name); //시간도 나오고 프로세스 아이디, 스레드, 컨트롤러 메세지까지 다 나옴.
        log.warn("warn log={}.{}",name);
        log.error("error log={}.{}",name);

        //로그의 레벨을 정할 수 있다.


        return "ok"; //Controller에서는 view 이름을 찾지만, RestController에서는 문자열을 그대로 반환
    }
}

/**
 * @RestController
 * @Controller 는 반환 값이 String 이면 뷰 이름으로 인식된다. 그래서 뷰를 찾고 뷰가 랜더링 된다.
 * @RestController 는 반환 값으로 뷰를 찾는 것이 아니라, HTTP 메시지 바디에 바로 입력한다.
 * 따라서 실행 결과로 ok 메세지를 받을 수 있다. @ResponseBody 와 관련이 있다.
 */

/**
 * log.debug("data="+data)
 * 로그 출력 레벨을 info로 설정해도 해당 코드에 있는 "data="+data가 실제 실행이 되어 버린다.
 * 결과적으로 문자 더하기 연산이 발생한다. 이렇게 사용하지 말자
 */