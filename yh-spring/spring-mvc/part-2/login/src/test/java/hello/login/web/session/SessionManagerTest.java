package hello.login.web.session;

import hello.login.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;

class SessionManagerTest {

    SessionManager sessionManager=new SessionManager();

    @Test
    void sessionTest(){

        MockHttpServletResponse response=new MockHttpServletResponse();

        //세션 생성
        Member member=new Member();
        sessionManager.createSession(member,response);

        //요청에 응답 쿠키 저장
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies()); //mySessionId =123ㅑ1ㅕ30 이런게 들어가있다.

        //세션 조회
        Object result = sessionManager.getSession(request);

        Assertions.assertThat(result).isEqualTo(member);

        //세션만료
        sessionManager.expire(request);
        Object expired= sessionManager.getSession(request);
        Assertions.assertThat(expired).isNull();
    }
    //간단하게 테스트를 진행해보자. 여기서는 HttpServletRequest , HttpservletResponse 객체를 직접
    //사용할 수 없기 때문에 테스트에서 비슷한 역할을 해주는 가짜 MockHttpServletRequest , MockHttpServletResponse 를 사용했다
}