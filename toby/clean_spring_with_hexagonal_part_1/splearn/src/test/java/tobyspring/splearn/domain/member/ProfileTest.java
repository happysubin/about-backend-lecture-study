package tobyspring.splearn.domain.member;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class ProfileTest {
    @Test
    void profile() {
        new Profile("tobyilee");
        new Profile("toby192");
        new Profile("12345");
    }

    @Test
    void profileFail() {
        Assertions.assertThatThrownBy(() -> new Profile("toolongtoolongtoolongtoolongtoolongtoolongtoolong")).isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatThrownBy(() -> new Profile("A")).isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatThrownBy(() -> new Profile("한국어")).isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    void url() {
        var profile = new Profile("tobyilee");

        Assertions.assertThat(profile.url()).isEqualTo("@tobyilee");
    }
}