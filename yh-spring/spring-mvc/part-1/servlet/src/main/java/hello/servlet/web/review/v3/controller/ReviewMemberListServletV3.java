package hello.servlet.web.review.v3.controller;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.review.ReviewModel;

import java.util.List;
import java.util.Map;

public class ReviewMemberListServletV3 implements ReviewControllerV3 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public ReviewModel process(Map<String, String> paramMap) {
        List<Member> members = memberRepository.findAll();

        ReviewModel model = new ReviewModel("members");
        model.getModel().put("members",members);

        return model;
    }
}
