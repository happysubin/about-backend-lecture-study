package tobyspring.splearn.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class MemberTest {

    Member member;
    PasswordEncoder encoder;

    @BeforeEach
    void setUp() {
        encoder = new PasswordEncoder() {
            @Override
            public String encode(String rawPassword) {
                return rawPassword.toUpperCase();
            }

            @Override
            public boolean matches(String rawPassword, String encodedPassword) {
                return encode(rawPassword).equals(encodedPassword);
            }
        };
        member = Member.create("subin@splearn.app", "Subin", "secret", encoder);
    }

    @Test
    void createMember() {
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void activate() {
        member.activate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    void activateFail() {
        member.activate();

        assertThatThrownBy(member::activate)
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void deactivate() {
        member.activate();

        member.deactivate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
    }

    @Test
    void deactivateFail() {
        member.activate();

        member.deactivate();

        assertThatThrownBy(member::deactivate)
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void verifyPassword() {
        assertThat(member.verifyPassword("secret", encoder)).isTrue();
        assertThat(member.verifyPassword("hello", encoder)).isFalse();
    }

    @Test
    void changeNickname() {
        assertThat(member.getNickname()).isEqualTo("Subin");
        member.changeNickname("subin");
        assertThat(member.getNickname()).isEqualTo("subin");
    }

    @Test
    void changePassword() {
        member.changePassword("verySecret", encoder);
        assertThat(member.verifyPassword("verySecret", encoder)).isTrue();
    }
}