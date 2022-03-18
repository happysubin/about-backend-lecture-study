package hello.servlet.web.review.v3;

import hello.servlet.web.review.ReviewModel;
import hello.servlet.web.review.ReviewView;
import hello.servlet.web.review.v3.controller.ReviewControllerV3;
import hello.servlet.web.review.v3.controller.ReviewMemberFormServletV3;
import hello.servlet.web.review.v3.controller.ReviewMemberListServletV3;
import hello.servlet.web.review.v3.controller.ReviewMemberSaveServletV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name="reviewFrontControllerV3", urlPatterns = "/review/v3/servlet-mvc/members/*")
public class FrontController extends HttpServlet {

    private HashMap<String, ReviewControllerV3> controllerMap = new HashMap<>();

    public FrontController() {
        controllerMap.put("/review/v3/servlet-mvc/members/new-form",new ReviewMemberFormServletV3());
        controllerMap.put("/review/v3/servlet-mvc/members/save",new ReviewMemberSaveServletV3());
        controllerMap.put("/review/v3/servlet-mvc/members/members",new ReviewMemberListServletV3());
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        ReviewControllerV3 controller = controllerMap.get(uri);

        if(controller == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return ;
        }
        Map<String, String> paramMap = createParamMap(request);
        ReviewModel model = controller.process(paramMap);

        String viewName = model.getViewName();
        ReviewView view = viewResolver(viewName);

        view.render(model.getModel(),request,response);


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
