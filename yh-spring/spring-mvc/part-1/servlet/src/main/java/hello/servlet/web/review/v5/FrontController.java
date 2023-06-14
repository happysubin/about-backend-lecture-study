package hello.servlet.web.review.v5;

import hello.servlet.web.review.ReviewModel;
import hello.servlet.web.review.ReviewView;
import hello.servlet.web.review.v3.controller.ReviewMemberFormServletV3;
import hello.servlet.web.review.v3.controller.ReviewMemberListServletV3;
import hello.servlet.web.review.v3.controller.ReviewMemberSaveServletV3;
import hello.servlet.web.review.v4.controller.ReviewControllerV4;
import hello.servlet.web.review.v4.controller.ReviewMemberFormServletV4;
import hello.servlet.web.review.v4.controller.ReviewMemberListServletV4;
import hello.servlet.web.review.v4.controller.ReviewMemberSaveServletV4;
import hello.servlet.web.review.v5.adapter.ReviewControllerV3HandlerAdapter;
import hello.servlet.web.review.v5.adapter.ReviewControllerV4HandlerAdapter;
import hello.servlet.web.review.v5.adapter.ReviewHandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name="reviewFrontControllerV5", urlPatterns = "/review/v5/*")
public class FrontController extends HttpServlet {

    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    private final List<ReviewHandlerAdapter> handlerAdapters = new ArrayList<>();

    public FrontController(){
        initHandlerMappingMap(); //핸들러 매핑 초기화. URI를 키로 핸들러(컨트롤러)를 맵에 넣는다
        initHandlerAdapters(); //어댑터 매핑 초기화. 어댑터 넣는다
    }

    private void initHandlerMappingMap(){
        handlerMappingMap.put("/review/v5/v3/servlet-mvc/members/new-form", new ReviewMemberFormServletV3());
        handlerMappingMap.put("/review/v5/v3/servlet-mvc/members/save", new ReviewMemberSaveServletV3());
        handlerMappingMap.put("/review/v5/v3/servlet-mvc/members/members", new ReviewMemberListServletV3());

        handlerMappingMap.put("/review/v5/v4/servlet-mvc/members/new-form", new ReviewMemberFormServletV4());
        handlerMappingMap.put("/review/v5/v4/servlet-mvc/members/save", new ReviewMemberSaveServletV4());
        handlerMappingMap.put("/review/v5/v4/servlet-mvc/members/members", new ReviewMemberListServletV4());
    }

    private void initHandlerAdapters(){
        handlerAdapters.add(new ReviewControllerV3HandlerAdapter());
        handlerAdapters.add(new ReviewControllerV4HandlerAdapter());
    }


    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object handler = getHandler(request);

        if(handler == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return ;
        }

        ReviewHandlerAdapter adapter = getHandlerAdapter(handler);
        ReviewModel mv = adapter.handle(request, response, handler);

        ReviewView view = viewResolver(mv.getViewName());

        view.render(mv.getModel(),request,response);


    }

    private Object getHandler(HttpServletRequest request){
        String uri = request.getRequestURI();
        return handlerMappingMap.get(uri);
    }

    private ReviewHandlerAdapter getHandlerAdapter(Object handler) {
        for (ReviewHandlerAdapter adapter : handlerAdapters) {
            if (adapter.supports(handler)) {
                return adapter;
            }
        }
        throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다" + handler);
    }

    private ReviewView viewResolver(String viewName){
        return new ReviewView("/WEB-INF/views/" + viewName +".jsp");
    }
}
