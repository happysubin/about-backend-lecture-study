package springapi.api.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;


@Getter
@Setter
public class User {
    private Long id;


    @Size(min=2,message = "Name는 2글자 이상 입력하세요") //한 글자만 넣으면 상태코드 400이 리턴증
    private String name;

    @Past// 과거 데이터만 사용 가능
    private Date joinDate;

    public User(Long id, String name, Date joinDate) {
        this.id = id;
        this.name = name;
        this.joinDate = joinDate;
    }
}
