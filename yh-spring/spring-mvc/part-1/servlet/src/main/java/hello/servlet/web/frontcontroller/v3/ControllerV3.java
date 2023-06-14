package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;

import java.util.Map;

public interface ControllerV3 {

    ModelView process(Map<String,String> paramMap) ;
}

/**
 * 컨트롤러 입장에서 HttpServletRequest, HttpServletResponse이 꼭 필요할까?
 * 요청 파라미터 정보는 자바의 Map으로 대신 넘기도록 하면 지금 구조에서는 컨트롤러가 서블릿 기술을
 * 몰라도 동작할 수 있다.
 * 그리고 request 객체를 Model로 사용하는 대신에 별도의 Model 객체를 만들어서 반환하면 된다.
 * 우리가 구현하는 컨트롤러가 서블릿 기술을 전혀 사용하지 않도록 변경해보자.
 * 이렇게 하면 구현 코드도 매우 단순해지고, 테스트 코드 작성이 쉽다.
 * 서블릿 기술 안 씀. 서블릿에 종속적이지 않아.
 */
