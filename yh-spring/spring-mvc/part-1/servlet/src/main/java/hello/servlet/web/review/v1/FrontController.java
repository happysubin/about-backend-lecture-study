package hello.servlet.web.review.v1;

import hello.servlet.web.review.v1.controller.ReviewControllerV1;
import hello.servlet.web.review.v1.controller.ReviewMemberFormServletV1;
import hello.servlet.web.review.v1.controller.ReviewMemberListServletV1;
import hello.servlet.web.review.v1.controller.ReviewMemberSaveServletV1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet(name="reviewFrontControllerV1", urlPatterns = "/review/v1/servlet-mvc/members/*")
public class FrontController extends HttpServlet
{
    private  HashMap<String, ReviewControllerV1> controllerMap = new HashMap<>();

    public FrontController() {
        controllerMap.put("/review/v1/servlet-mvc/members/new-form",new ReviewMemberFormServletV1());
        controllerMap.put("/review/v1/servlet-mvc/members/save",new ReviewMemberSaveServletV1());
        controllerMap.put("/review/v1/servlet-mvc/members/members",new ReviewMemberListServletV1());
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();

        ReviewControllerV1 controller = controllerMap.get(uri);
        if(controller == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return ;
        }
        controller.process(request,response);
    }
}
