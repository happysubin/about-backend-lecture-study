package hello.servlet.web.review.v2;

import hello.servlet.web.review.ReviewView;
import hello.servlet.web.review.v2.controller.ReviewControllerV2;
import hello.servlet.web.review.v2.controller.ReviewMemberFormServletV2;
import hello.servlet.web.review.v2.controller.ReviewMemberListServletV2;
import hello.servlet.web.review.v2.controller.ReviewMemberSaveServletV2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet(name="reviewFrontControllerV2", urlPatterns = "/review/v2/servlet-mvc/members/*")
public class FrontController extends HttpServlet {

    private HashMap<String, ReviewControllerV2> controllerMap = new HashMap<>();

    public FrontController() {
        controllerMap.put("/review/v2/servlet-mvc/members/new-form",new ReviewMemberFormServletV2());
        controllerMap.put("/review/v2/servlet-mvc/members/save",new ReviewMemberSaveServletV2());
        controllerMap.put("/review/v2/servlet-mvc/members/members",new ReviewMemberListServletV2());
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        ReviewControllerV2 controller = controllerMap.get(uri);

        if(controller == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return ;
        }

        ReviewView view= controller.process(request, response);
        view.render(request,response);
    }


}
