package hello.servlet.basic.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name="responseHtmlServlet",urlPatterns = "/response-html")
public class ResponseHtmlServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html"); //1. 타입을 정해
        res.setCharacterEncoding("utf-8");//2. 인코딩을 정함
        PrintWriter writer = res.getWriter();//적기 시작
        writer.println("<html>");
        writer.println("<body>");
        writer.println(" <div>안녕?</div>");
        writer.println("</body>");
        writer.println("</html>");
    }
}
/**
 * HTTP 응답 메시지는 주로 다음 내용을 담아서 전달한다.
 * 단순 텍스트 응답
 * 앞에서 살펴봄 ( writer.println("ok"); )
 * HTML 응답
 * HTTP API - MessageBody JSON 응답
 */
