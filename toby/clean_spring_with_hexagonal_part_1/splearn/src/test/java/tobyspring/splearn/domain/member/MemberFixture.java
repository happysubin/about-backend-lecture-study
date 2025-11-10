package tobyspring.splearn.domain.member;

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
}
