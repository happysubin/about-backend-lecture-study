package hello.servlet.web.review.v4.controller;

import hello.servlet.web.review.ReviewModel;

import java.util.Map;

public interface ReviewControllerV4 {
  String process(Map<String, String> paramMap,Map<String, Object> model);
}
