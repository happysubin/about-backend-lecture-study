package hello.exception.servlet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Controller
@Slf4j
public class ServletExController {

    @GetMapping("/error-ex")
    public void errorEx(){
        throw new RuntimeException("예외 발생!");
    }
    //Exception 의 경우 서버 내부에서 처리할 수 없는 오류가 발생한 것으로 생각해서 HTTP 상태 코드 500을 반환한다.

    @GetMapping("/error-404")
    public void error404(HttpServletResponse response) throws IOException {
        response.sendError(404, "404 오류!");
    }

    @GetMapping("/error-500")
    public void error500(HttpServletResponse response) throws IOException {
        response.sendError(500);
    }

    @GetMapping("/error-400")
    public void error400(HttpServletResponse response) throws IOException {
        response.sendError(400);
    }
    @GetMapping("/error-401")
    public void error401(HttpServletResponse response) throws IOException {
        response.sendError(401);
    }
}

/**
 * 서블릿은 다음 2가지 방식으로 예외 처리를 지원한다.
 *
 * Exception (예외)
 * response.sendError(HTTP 상태 코드, 오류 메시지)
 */


/**
 * 개발자는 오류 페이지만 등록
 * BasicErrorController 는 기본적인 로직이 모두 개발되어 있다.
 * 개발자는 오류 페이지 화면만 BasicErrorController 가 제공하는 룰과 우선순위에 따라서 등록하면
 * 된다. 정적 HTML이면 정적 리소스, 뷰 템플릿을 사용해서 동적으로 오류 화면을 만들고 싶으면 뷰 템플릿
 * 경로에 오류 페이지 파일을 만들어서 넣어두기만 하면 된다.
 *
 * 즉 response.sendError에 넣은 상태코드와 맞는 이름을 가진 html을 렌더링한다.
 */