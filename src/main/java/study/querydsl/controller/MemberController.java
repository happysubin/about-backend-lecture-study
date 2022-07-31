package study.querydsl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import study.querydsl.dto.MemberSearchCondition;
import study.querydsl.dto.MemberTeamDto;
import study.querydsl.repository.MemberJpaRepository;
import study.querydsl.repository.MemberRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberJpaRepository memberJpaRepository;
    private final MemberRepository memberRepository;

    @GetMapping("/v1/members")
    public List<MemberTeamDto> searchMemberV1(MemberSearchCondition condition) {
        return memberJpaRepository.search(condition);
    }

//    @GetMapping("/v2/members")
//    public Page<MemberTeamDto> searchMemberV2(MemberSearchCondition condition,
//                                              Pageable pageable) {
//        return memberRepository.searchPageSimple(condition, pageable);
//    } 아예 deprecated되어서 작성하지 않았다.

    @GetMapping("/v3/members")
    public Page<MemberTeamDto> searchMemberV3(MemberSearchCondition condition, Pageable pageable) { //컨트롤러가 페이저블을 바인딩해서 준다.
        return memberRepository.searchPageComplex(condition, pageable);
    }
    //예시 url http://localhost:8080/v3/members?page=0&size=5
}
