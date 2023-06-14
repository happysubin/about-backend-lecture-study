package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.ControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberFormControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberListControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberSaveControllerV2;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import org.springframework.boot.Banner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name="frontControllerServletV3",urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {

    private Map<String,ControllerV3> controllerMap=new HashMap<>();

    public FrontControllerServletV3() {
        controllerMap.put("/front-controller/v3/members/new-form",new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save",new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members",new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        ControllerV3 controller = controllerMap.get(requestURI); //다형성을 이용

        if(controller==null) {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //paramMap 모든 req의 파라미터를 가져와서 map에 넣는다
        Map<String, String> paramMap=createParamMap(req);
        ModelView mv = controller.process(paramMap);

        String viewName=mv.getViewName();

        MyView view = viewResolver(viewName);
        view.render(mv.getModel(),req,res);
    }

    //로직이 복잡하면 로직의 수준을 맞춰줘야함
    private Map<String,String> createParamMap(HttpServletRequest req) {
        Map<String, String> paramMap=new HashMap<>();
        req.getParameterNames().asIterator().
                forEachRemaining(paramName-> paramMap.put(paramName, req.getParameter(paramName)));
        return paramMap; //파라미터를 전부 뽑아서 넣어버림
    }

    private MyView viewResolver(String viewName){
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }
}

/**
 * 뷰 이름 중복 제거
 * 컨트롤러에서 지정하는 뷰 이름에 중복이 있는 것을 확인할 수 있다.
 * 컨트롤러는 뷰의 논리 이름을 반환하고, 실제 물리 위치의 이름은 프론트 컨트롤러에서 처리하도록 단순화
 * 하자.
 * 이렇게 해두면 향후 뷰의 폴더 위치가 함께 이동해도 프론트 컨트롤러만 고치면 된다
 *
 * /WEB-INF/views/new-form.jsp new-form
 * /WEB-INF/views/save-result.jsp save-result
 * /WEB-INF/views/members.jsp members
 *
 */