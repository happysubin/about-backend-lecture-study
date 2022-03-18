package hello.servlet.web.review.v1.controller;


import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ReviewMemberFormServletV1 implements ReviewControllerV1 {


    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        String viewPath = "/WEB-INF/views/new-form.jsp";
        RequestDispatcher dispatcher=request.getRequestDispatcher(viewPath);
        dispatcher.forward(request,response);
    }
}
