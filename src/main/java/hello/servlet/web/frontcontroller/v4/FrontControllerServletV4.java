package hello.servlet.web.frontcontroller.v4;


import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name="frontControllerServletV4",urlPatterns = "/front-controller/v4/*")
public class FrontControllerServletV4 extends HttpServlet {

    private Map<String, ControllerV4> controllerMap=new HashMap<>();

    public FrontControllerServletV4() {
        controllerMap.put("/front-controller/v4/members/new-form",new MemberFormControllerV4());
        controllerMap.put("/front-controller/v4/members/save",new MemberSaveControllerV4());
        controllerMap.put("/front-controller/v4/members",new MemberListControllerV4());
    }
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String requestURI = req.getRequestURI();
        ControllerV4 controller = controllerMap.get(requestURI); //다형성을 이용

        if(controller==null) {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //paramMap 모든 req의 파라미터를 가져와서 map에 넣는다
        Map<String, String> paramMap=createParamMap(req);
        Map<String, Object> model=new HashMap<>(); //추가
        String viewName = controller.process(paramMap,model);


        MyView view = viewResolver(viewName);
        view.render(model,req,res);
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

//코드를 정말 조금 수정함.

/**
 *이번 버전의 컨트롤러는 매우 단순하고 실용적이다. 기존 구조에서 모델을 파라미터로 넘기고, 뷰의 논리
 * 이름을 반환한다는 작은 아이디어를 적용했을 뿐인데, 컨트롤러를 구현하는 개발자 입장에서 보면 이제
 * 군더더기 없는 코드를 작성할 수 있다.
 * 또한 중요한 사실은 여기까지 한번에 온 것이 아니라는 점이다.
 *
 */