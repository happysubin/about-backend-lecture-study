package tobyspring.splearn.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class MemberTest {

    @Test
    void createMember() {
        Member member = new Member("subin@splearn.app", "Subin", "Secret");
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    // 연습용으로 하는거지 실무에서는 이런 코드 ㄴㄴ
    @Test
    void constructorNullCheck() {
        assertThatThrownBy(() -> new Member(null, "Subin", "Secret"))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void activate() {
        var member = new Member("subin", "subin", "secret");
        member.activate();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    void activateFail() {
        var member = new Member("subin", "subin", "secret");

        member.activate();

        assertThatThrownBy(member::activate)
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void deactivate() {
        var member = new Member("subin", "subin", "secret");
        member.activate();

        member.deactivate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
    }

    @Test
    void deactivateFail() {
        var member = new Member("subin", "subin", "secret");
        member.activate();

        member.deactivate();

        assertThatThrownBy(member::deactivate)
                .isInstanceOf(IllegalStateException.class);
    }
}