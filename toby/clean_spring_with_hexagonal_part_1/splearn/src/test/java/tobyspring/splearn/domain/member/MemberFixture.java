package tobyspring.splearn.domain.member;

import org.springframework.test.util.ReflectionTestUtils;

public class MemberFixture {
    public static MemberRegisterRequest createMemberRegisterRequest() {
        return new MemberRegisterRequest("subin@splearn.app", "Subin", "long___secret");
    }

    public static MemberRegisterRequest createMemberRegisterRequest(String email) {
        return new MemberRegisterRequest(email, "Subin", "long____secret");
    }

    public static PasswordEncoder createPasswordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(String rawPassword) {
                return rawPassword.toUpperCase();
            }

            @Override
            public boolean matches(String rawPassword, String encodedPassword) {
                return encode(rawPassword).equals(encodedPassword);
            }
        };
    }

    public static Member createMember(Long id) {
        Member member = Member.register(createMemberRegisterRequest(), createPasswordEncoder());
        ReflectionTestUtils.setField(member, "id", id);
        return member;
    }
}
