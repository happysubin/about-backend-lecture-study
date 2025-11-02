package tobyspring.splearn.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static tobyspring.splearn.domain.MemberFixture.createMemberRegisterRequest;
import static tobyspring.splearn.domain.MemberFixture.createPasswordEncoder;

class MemberTest {

    Member member;
    PasswordEncoder encoder;

    @BeforeEach
    void setUp() {
        encoder = createPasswordEncoder();
        member = Member.register(createMemberRegisterRequest(), encoder);
    }

    @Test
    void registerMember() {
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

    @Test
    void isActive() {
        assertThat(member.isActive()).isFalse();

        member.activate();

        assertThat(member.isActive()).isTrue();

        member.deactivate();

        assertThat(member.isActive()).isFalse();
    }

    @Test
    void invalidEmail() {
        assertThatThrownBy(() -> {
            Member.register(createMemberRegisterRequest("invalid email"), encoder);
        }).isInstanceOf(IllegalArgumentException.class);

        Member.register(createMemberRegisterRequest(), encoder);
    }
}