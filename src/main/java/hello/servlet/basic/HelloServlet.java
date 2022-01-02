package hello.servlet.basic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="helloServlet",urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {//서블릿은 이것을 상속 받아야함.

    @Override //서블릿이 호출되면 서비스 메소드가 실행된다.
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        System.out.println("req = " + req);
        System.out.println("req.getParameter(\"username\") = " + req.getParameter("username"));
        System.out.println("res = " + res);

        System.out.println("HelloServlet.service");
        res.setContentType("text/plain");//리턴할 콘텐츠 타입 설정
        res.setCharacterEncoding("utf-8"); //인코딩 설정 어디서나 웬만하면 utf-8 사용
        res.getWriter().write("hello  "+req.getParameter("username"));//http resposne body에 들어감
    }
}
