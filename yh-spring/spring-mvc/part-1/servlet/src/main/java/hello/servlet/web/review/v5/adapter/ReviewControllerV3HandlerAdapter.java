package hello.servlet.web.review.v5.adapter;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.review.ReviewModel;
import hello.servlet.web.review.v3.controller.ReviewControllerV3;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ReviewControllerV3HandlerAdapter implements ReviewHandlerAdapter{
    @Override
    public boolean supports(Object handler) {
        return (handler instanceof ReviewControllerV3); //다형성을 이용해 컨트롤러3 계열인지 체크
    }

    @Override
    public ReviewModel handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        ReviewControllerV3 controller = (ReviewControllerV3) handler;
        Map<String, String> paramMap = createParamMap(request);

        ReviewModel mv = controller.process(paramMap);
        return mv;
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap =new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName,request.getParameter(paramName)));

        return paramMap;
    }
}
