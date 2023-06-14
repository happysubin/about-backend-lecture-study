package hello.servlet.web.review.v2.controller;

import hello.servlet.web.review.ReviewView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ReviewControllerV2 {
    ReviewView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
