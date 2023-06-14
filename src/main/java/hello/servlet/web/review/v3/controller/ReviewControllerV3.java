package hello.servlet.web.review.v3.controller;

import hello.servlet.web.review.ReviewModel;
import hello.servlet.web.review.ReviewView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public interface ReviewControllerV3 {
  ReviewModel process(Map<String, String> paramMap);
}
