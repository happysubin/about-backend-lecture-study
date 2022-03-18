package hello.servlet.web.review.v3.controller;

import hello.servlet.web.review.ReviewModel;
import hello.servlet.web.review.v3.controller.ReviewControllerV3;

import java.util.Map;

public class ReviewMemberFormServletV3 implements ReviewControllerV3 {
    @Override
    public ReviewModel process(Map<String, String> paramMap) {
        return new ReviewModel("new-form");
    }
}
