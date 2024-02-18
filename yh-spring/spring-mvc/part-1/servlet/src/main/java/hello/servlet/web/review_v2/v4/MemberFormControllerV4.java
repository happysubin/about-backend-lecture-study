package hello.servlet.web.review_v2.v4;

import hello.servlet.web.review_v2.v3.ControllerV3;
import hello.servlet.web.review_v2.v3.ModelView;

import java.util.Map;

public class MemberFormControllerV4 implements ControllerV4 {

    @Override
    public String process(Map<String, String> paramMap,  Map<String, Object> model) {
        return "new-form";
    }
}
