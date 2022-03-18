package hello.servlet.web.review.v4;

import hello.servlet.web.review.ReviewView;
import hello.servlet.web.review.v3.controller.ReviewControllerV3;
import hello.servlet.web.review.v3.controller.ReviewMemberFormServletV3;
import hello.servlet.web.review.v3.controller.ReviewMemberListServletV3;
import hello.servlet.web.review.v3.controller.ReviewMemberSaveServletV3;
import hello.servlet.web.review.v4.controller.ReviewControllerV4;
import hello.servlet.web.review.v4.controller.ReviewMemberFormServletV4;
import hello.servlet.web.review.v4.controller.ReviewMemberListServletV4;
import hello.servlet.web.review.v4.controller.ReviewMemberSaveServletV4;
import org.springframework.web.servlet.view.script.ScriptTemplateViewResolver;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name="reviewFrontControllerV4", urlPatterns = "/review/v4/servlet-mvc/members/*")
public class FrontController extends HttpServlet {
    private HashMap<String, ReviewControllerV4> controllerMap = new HashMap<>();

    public FrontController() {
        controllerMap.put("/review/v4/servlet-mvc/members/new-form",new ReviewMemberFormServletV4());
        controllerMap.put("/review/v4/servlet-mvc/members/save",new ReviewMemberSaveServletV4());
        controllerMap.put("/review/v4/servlet-mvc/members/members",new ReviewMemberListServletV4());
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();

        ReviewControllerV4 controller =controllerMap.get(uri);

        if(controller == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return ;
        }

        Map<String, String> paramMap = createParamMap(request);
        Map<String, Object> model =new HashMap<>();

        String viewName = controller.process(paramMap, model);

        ReviewView reviewView = viewResolver(viewName);
        reviewView.render(model,request,response);

    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap =new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName,request.getParameter(paramName)));

        return paramMap;
    }

    private ReviewView viewResolver(String viewName){
        return new ReviewView("/WEB-INF/views/" + viewName +".jsp");
    }

}
