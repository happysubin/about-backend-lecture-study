package hello.servlet.web.review.v4.controller;

import java.util.Map;

public class ReviewMemberFormServletV4 implements ReviewControllerV4{
    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        return "new-form";
    }
}
