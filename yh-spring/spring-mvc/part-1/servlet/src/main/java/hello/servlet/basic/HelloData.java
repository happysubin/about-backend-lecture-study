package hello.servlet.basic;

import lombok.Getter;
import lombok.Setter;

//JSON 형식으로 파싱할 수 있는 객체를 만들기 위한 클래스.
@Getter @Setter
public class HelloData {
    private String username;
    private int age;

/* 배운것처럼 롬복이 아래 코드를 대신 뚝딱 실행해줌
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;

    }
 */
}
