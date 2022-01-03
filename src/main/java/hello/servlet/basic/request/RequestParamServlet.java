package hello.servlet.basic.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * 1. 파라미터 전송 기능
 * http://localhost:8080/request-param?username=hello&age=20
 * <p>
 * 2. 동일한 파라미터 전송 가능
 * http://localhost:8080/request-param?username=hello&username=kim&age=20
 */

@WebServlet(name="requestParamServlet",urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
                /*
         Enumeration<String> parameterNames = req.getParameterNames();
         while (parameterNames.hasMoreElements()) {
         String paramName = parameterNames.nextElement();
         System.out.println(paramName + "=" +
        req.getParameter(paramName));
         }
         */
        req.getParameterNames().asIterator().forEachRemaining(paramName->
                System.out.println(paramName + " = "+req.getParameter(paramName)));
                //좌측 param Name key, 우측은 paramName에 대한 값 value

        System.out.println("[전체 파라미터 조회] - end");
        System.out.println();

        System.out.println("[단일 파라미터 조회]");
        String username = req.getParameter("username");
        System.out.println("request.getParameter(username) = " + username);

        String age = req.getParameter("age");
        System.out.println("request.getParameter(age) = " + age);
        System.out.println();

        System.out.println("[이름이 같은 복수 파라미터 조회]");
        System.out.println("request.getParameterValues(username)");
        String[] usernames = req.getParameterValues("username"); //하나의 파라미터에 (key) 여러 값 넣기 가능
        for (String name : usernames) {
            System.out.println("username=" + name);
        }
        //참고로 이렇게 파라미터가 중복일 때 request.getParameter() 를 사용하면 request.getParameterValues() 의 첫 번째 값을 반환한다.
        res.getWriter().write("ok");
    }
}


/**
 * application/x-www-form-urlencoded 형식은 앞서 GET에서 살펴본 쿼리 파라미터 형식과 같다.
 * 따라서 쿼리 파라미터 조회 메서드를 그대로 사용하면 된다.
 * 클라이언트(웹 브라우저) 입장에서는 두 방식에 차이가 있지만, 서버 입장에서는 둘의 형식이 동일하므로,
 * request.getParameter() 로 편리하게 구분없이 조회할 수 있다.
 * 정리하면 request.getParameter() 는 GET URL 쿼리 파라미터 형식도 지원하고, POST HTML Form 형식도 둘 다 지원한다.
 *
 * POST HTML Form 형식으로 데이터를 전달하면 HTTP 메시지 바디에 해당 데이터를 포함해서 보내기
 * 때문에 바디에 포함된 데이터가 어떤 형식인지 content-type을 꼭 지정해야 한다.
 *
 * 쿼리파라미터 ! 폼 형식 둘다 똑같이 지원!!!!
 */