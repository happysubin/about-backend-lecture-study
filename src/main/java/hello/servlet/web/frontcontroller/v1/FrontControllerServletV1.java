package hello.servlet.web.frontcontroller.v1;

import hello.servlet.web.frontcontroller.v1.controller.MemberFormControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberListControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberSaveControllerV1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@WebServlet(name="frontControllerServletV1",urlPatterns = "/front-controller/v1/*")//v1 슬래쉬 이후에 뭐가들어와도 이 서블릿이 호출됨
public class FrontControllerServletV1 extends HttpServlet {

    private Map<String,ControllerV1> controllerMap=new HashMap<>();

    public FrontControllerServletV1() {
        controllerMap.put("/front-controller/v1/members/new-form",new MemberFormControllerV1());
        controllerMap.put("/front-controller/v1/members/save",new MemberSaveControllerV1());
        controllerMap.put("/front-controller/v1/members",new MemberListControllerV1());
    }
    //뭔가 라우터 느낌

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String requestURI = req.getRequestURI();
        System.out.println("requestURI = " + requestURI);

        ControllerV1 controller = controllerMap.get(requestURI); //다형성을 이용
        System.out.println("controllerV1 = " + controller);

        if(controller==null) {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        controller.process(req,res);
    }
}

//진짜 장점은 진입점이 하나로 모인다는 것이다.