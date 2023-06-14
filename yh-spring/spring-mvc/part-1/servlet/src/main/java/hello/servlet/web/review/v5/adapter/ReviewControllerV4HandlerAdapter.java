package hello.servlet.web.review.v5.adapter;

import hello.servlet.web.frontcontroller.v4.ControllerV4;
import hello.servlet.web.review.ReviewModel;
import hello.servlet.web.review.ReviewView;
import hello.servlet.web.review.v4.controller.ReviewControllerV4;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ReviewControllerV4HandlerAdapter implements ReviewHandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return (handler instanceof ReviewControllerV4);
    }

    @Override
    public ReviewModel handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        ReviewControllerV4 controller = (ReviewControllerV4) handler;

        Map<String, String>  paramMap = createParamMap(request);
        Map<String, Object>  model = new HashMap<>();

        String viewName = controller.process(paramMap,model);

        ReviewModel mv =new ReviewModel(viewName);

        mv.setModel(model);

        return mv;
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap =new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName,request.getParameter(paramName)));

        return paramMap;
    }
}
