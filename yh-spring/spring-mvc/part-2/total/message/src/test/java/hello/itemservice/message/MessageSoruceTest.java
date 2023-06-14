package hello.itemservice.message;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest //스프링부트가 돌면서 자동으로 메시지 소스 등록
public class MessageSoruceTest {
    
    @Autowired 
    MessageSource ms;
    
    @Test
    void helloMessages(){
        String result=ms.getMessage("hello",null,null);//code,args,null 이러면 default가 실행됨
        Assertions.assertThat(result).isEqualTo("안녕");
    }

    void notFoundMessageCode() {
        assertThatThrownBy(() -> ms.getMessage("no_code", null, null))
                .isInstanceOf(NoSuchMessageException.class);
    }
    @Test
    void notFoundMessageCodeDefaultMessage() {
        String result = ms.getMessage("no_code", null, "기본 메시지", null);
        assertThat(result).isEqualTo("기본 메시지");
    }

    //메시지가 없는 경우에는 NoSuchMessageException 이 발생한다.
    //메시지가 없어도 기본 메시지( defaultMessage )를 사용하면 기본 메시지가 반환된다.

    @Test
    void argumentMessage() {
        String result = ms.getMessage("hello.name", new Object[]{"Spring"}, null);
        assertThat(result).isEqualTo("안녕 Spring");
    }
    //다음 메시지의 {0} 부분은 매개변수를 전달해서 치환할 수 있다.
    //hello.name=안녕 {0} Spring 단어를 매개변수로 전달 안녕 Spring

    @Test
    void defaultLang() {
        assertThat(ms.getMessage("hello", null, null)).isEqualTo("안녕");
        assertThat(ms.getMessage("hello", null, Locale.KOREA)).isEqualTo("안녕");
    }

    @Test
    void enLang(){
        assertThat(ms.getMessage("hello",null,Locale.ENGLISH)).isEqualTo("hello");
    }
}
