package hello.servlet.web.frontcontroller.v5;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name="frontControllerServletV5",urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    private final Map<String, Object> handlerMappingMap = new HashMap<>(); //아무 컨트롤러나 다 들어가야해서 Object
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();//어뎁터를 넣는다.

    public FrontControllerServletV5() {
        initHandlerMappingMap();
        initHandlerAdapters();
    }

    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add (new ControllerV4HandlerAdapter()); //V4 추가
    }

    private void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new
                MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new
                MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new
                MemberListControllerV3());

        //V4 추가
        handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new
                MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new
                MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members", new
                MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Object handler=getHandler(req);

        if (handler == null) {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        MyHandlerAdapter adapter = getHandlerAdapter(handler);
        ModelView mv = adapter.handle(req, res, handler);

        MyView view = viewResolver(mv.getViewName());
        view.render(mv.getModel(), req, res);
    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return handlerMappingMap.get(requestURI);
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler){
        for(MyHandlerAdapter adapter:handlerAdapters){
            if(adapter.supports(handler)){
                return adapter;
            }
        }
        throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다. handler=" + handler);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }
}

/**정리
 지금까지 v1 ~ v5로 점진적으로 프레임워크를 발전시켜 왔다.
 지금까지 한 작업을 정리해보자.
 v1: 프론트 컨트롤러를 도입
 기존 구조를 최대한 유지하면서 프론트 컨트롤러를 도입
 v2: View 분류
 단순 반복 되는 뷰 로직 분리
 v3: Model 추가
 서블릿 종속성 제거
 뷰 이름 중복 제거
 v4: 단순하고 실용적인 컨트롤러
 v3와 거의 비슷
 구현 입장에서 ModelView를 직접 생성해서 반환하지 않도록 편리한 인터페이스 제공
 v5: 유연한 컨트롤러
 어댑터 도입
 어댑터를 추가해서 프레임워크를 유연하고 확장성 있게 설계
 *
 */