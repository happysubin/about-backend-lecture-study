package hello.servlet.web.review.v2.controller;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.review.ReviewView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ReviewMemberListServletV2 implements ReviewControllerV2{

    private MemberRepository memberRepository = MemberRepository.getInstance();


    @Override
    public ReviewView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Member> members = memberRepository.findAll();
        request.setAttribute("members",members);

        return new ReviewView("/WEB-INF/views/members.jsp");

    }
}
