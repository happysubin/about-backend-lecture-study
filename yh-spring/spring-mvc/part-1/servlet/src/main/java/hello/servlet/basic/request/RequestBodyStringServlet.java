package hello.servlet.basic.request;


import org.springframework.util.StreamUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * HTTP message body에 데이터를 직접 담아서 요청
 * HTTP API에서 주로 사용, JSON, XML, TEXT
 * 데이터 형식은 주로 JSON 사용
 * POST, PUT, PATCH
 *
 * InputStream을 이용해 텍스트를 읽을 수 있다.
 */

@WebServlet(name = "requestBodyStringServlet", urlPatterns = "/request-body-string")
public class RequestBodyStringServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        ServletInputStream inputStream = req.getInputStream();
        String messageBody=StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);//인코딩 방식을 알려줌
        //> inputStream은 byte 코드를 반환한다. byte 코드를 우리가 읽을 수 있는 문자(String)로 보려면 문자표
        //(Charset)를 지정해주어야 한다. 여기서는 UTF_8 Charset을 지정해주었다.

        System.out.println("messageBody = " + messageBody);
        res.getWriter().write("ok");
    }
}
