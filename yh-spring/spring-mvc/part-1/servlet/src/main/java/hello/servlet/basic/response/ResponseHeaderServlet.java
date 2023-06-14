package hello.servlet.basic.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *  response의 큰 기능
 *  HTTP 응답코드 지정
 * 헤더 생성
 * 바디 생성
 * 편의 기능 제공
 *
 * Content-Type, 쿠키, Redirect
 */

@WebServlet(name = "responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //status-line
        res.setStatus(HttpServletResponse.SC_OK);//http 응답 코드를 넣는다. 직접 적는거보단 상수를 쓰는게 좋다.

        //[response-headers]
        res.setHeader("Content-Type", "text/plain;charset=utf-8");
        res.setHeader("Cache-Control", "no-cache, no-store, mustrevalidate");
        res.setHeader("Pragma", "no-cache");
        res.setHeader("my-header","hello"); //임의의 헤더

        //크롬 개발자 도구에서 확인 가능.

        //[Header 편의 메서드]
        content(res);
       cookie(res);
       redirect(res);
        //[message body]
        PrintWriter writer = res.getWriter();
        writer.println("ok");
    }

    private void content(HttpServletResponse response) {
        //Content-Type: text/plain;charset=utf-8
        //Content-Length: 2
        //response.setHeader("Content-Type", "text/plain;charset=utf-8");
        response.setContentType("text/plain"); //이전에는 직접 설정. 좀 더 편한 설정 방법
        response.setCharacterEncoding("utf-8");
        //response.setContentLength(2); //(생략시 계산 되어서 자동 생성)
    }

    private void cookie(HttpServletResponse response) {
        //Set-Cookie: myCookie=good; Max-Age=600;
        //response.setHeader("Set-Cookie", "myCookie=good; Max-Age=600");
        Cookie cookie = new Cookie("myCookie", "good"); //편리한 방법. 위와 같이 직접 세팅 X
        cookie.setMaxAge(600); //600초
        response.addCookie(cookie);
    }

    private void redirect(HttpServletResponse response) throws IOException {
        //Status Code 302
        //Location: /basic/hello-form.html
        //response.setStatus(HttpServletResponse.SC_FOUND); //302
        //response.setHeader("Location", "/basic/hello-form.html");
        response.sendRedirect("/basic/hello-form.html");
    }
}
