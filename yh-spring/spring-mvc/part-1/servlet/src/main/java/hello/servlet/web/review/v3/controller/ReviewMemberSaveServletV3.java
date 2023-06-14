package hello.servlet.web.review.v3.controller;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.review.ReviewModel;
import hello.servlet.web.review.v3.controller.ReviewControllerV3;

import java.util.Map;

public class ReviewMemberSaveServletV3 implements ReviewControllerV3 {

    private MemberRepository memberRepository = MemberRepository.getInstance();
    @Override
    public ReviewModel process(Map<String, String> paramMap) {
        String username = paramMap.get("username");
        int age = Integer.parseInt(paramMap.get("age"));

        Member member = new Member(username, age);
        memberRepository.save(member);

        ReviewModel model = new ReviewModel("save-result");
        model.getModel().put("member",member);
        return model;

    }
}
