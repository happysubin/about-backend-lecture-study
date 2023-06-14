package tobyspring.myboot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloApiTest {

    @Test
    void hello(){

        //given
        TestRestTemplate restTemplate = new TestRestTemplate();

        //when
        ResponseEntity<String> res = restTemplate.getForEntity("http://localhost:8080/hello?name={name}", String.class, "Spring");

        //then
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(res.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE).startsWith(MediaType.TEXT_PLAIN_VALUE)).isTrue();
        assertThat(res.getBody().trim()).isEqualTo("* Hello Spring *");
    }


    @Test
    void failApiCase1(){

        //given
        TestRestTemplate restTemplate = new TestRestTemplate();

        //when
        ResponseEntity<String> res = restTemplate.getForEntity("http://localhost:8080/hello?name={name}", String.class, "");

        //then
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void failApiCase2(){

        //given
        TestRestTemplate restTemplate = new TestRestTemplate();

        //when
        ResponseEntity<String> res = restTemplate.getForEntity("http://localhost:8080/hello?name=", String.class);

        //then
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
