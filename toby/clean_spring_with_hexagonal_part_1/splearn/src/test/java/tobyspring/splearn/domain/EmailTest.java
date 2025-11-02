package tobyspring.splearn.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class EmailTest {

    @Test
    void equality() {
        Email email1 = new Email("subin@splearn.app");
        Email email2 = new Email("subin@splearn.app");

        Assertions.assertThat(email1).isEqualTo(email2);
    }
}