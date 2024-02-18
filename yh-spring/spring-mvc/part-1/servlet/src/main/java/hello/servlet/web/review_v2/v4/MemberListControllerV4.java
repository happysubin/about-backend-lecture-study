package hello.servlet.web.review_v2.v4;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.review_v2.v3.ControllerV3;
import hello.servlet.web.review_v2.v3.ModelView;

import java.util.List;
import java.util.Map;

public class MemberListControllerV4 implements ControllerV4 {
    private final MemberRepository memberRepository = MemberRepository.getInstance();
    @Override
    public String process(Map<String, String> paramMap,  Map<String, Object> model) {
        List<Member> members = memberRepository.findAll();
        model.put("members", members);
        return "members";
    }
}
