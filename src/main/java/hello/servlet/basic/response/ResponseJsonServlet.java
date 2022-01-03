package hello.servlet.basic.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.servlet.basic.HelloData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="responseJsonServlet",urlPatterns ="/response-json")
public class ResponseJsonServlet extends HttpServlet {
    private ObjectMapper objectMapper=new ObjectMapper();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json"); //컨텐트 타입 지정
        res.setCharacterEncoding("utf-8"); // 인코딩 방식 지정

        HelloData helloData=new HelloData();
        helloData.setUsername("kim");
        helloData.setAge(20);

        //{"username":"kim","age":20}
        String result=objectMapper.writeValueAsString(helloData);
        res.getWriter().write(result);
    }
}
