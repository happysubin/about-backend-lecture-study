package hello.servlet.web.review_v2.v4;

import hello.servlet.web.review_v2.v3.ModelView;

import java.util.Map;

public interface ControllerV4 {
    String process(Map<String, String> paramMap,  Map<String, Object> model);
}
