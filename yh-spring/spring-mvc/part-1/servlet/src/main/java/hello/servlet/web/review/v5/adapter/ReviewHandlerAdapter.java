package hello.servlet.web.review.v5.adapter;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.review.ReviewModel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ReviewHandlerAdapter {
    boolean supports (Object handler); //어댑터가 컨트롤러를 처리할 수 있는지 판단

    ReviewModel handle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws ServletException, IOException; //실제 컨트롤러(핸들러)를 호출하고, 그 결과로 모델을 반환
}
