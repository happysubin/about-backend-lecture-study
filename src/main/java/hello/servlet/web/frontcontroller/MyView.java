package hello.servlet.web.frontcontroller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyView {
    private String viewPath;

    public MyView(String viewPath) {
        this.viewPath = viewPath;
    }

    public void render(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException {
        RequestDispatcher dispatcher=req.getRequestDispatcher(viewPath);
        dispatcher.forward(req,res);
        }
}

/**
 *
 * 모든 컨트롤러에서 뷰로 이동하는 부분에 중복이 있고, 깔끔하지 않다.
 * String viewPath = "/WEB-INF/views/new-form.jsp";
 * RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
 * dispatcher.forward(request, response);
 * 이 부분을 깔끔하게 분리하기 위해 별도로 뷰를 처리하는 객체를 만듬.
 *
 */