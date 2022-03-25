package springapi.api.domain;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;


@Data
@AllArgsConstructor
//@JsonIgnoreProperties(value = {"password"}) //password만 제외딘다. 클래스 상단에서 사용
@JsonFilter("UserInfo")
public class User {
    private Long id;

    @Size(min=2,message = "Name는 2글자 이상 입력하세요") //한 글자만 넣으면 상태코드 400이 리턴증
    private String name;

    @Past// 과거 데이터만 사용 가능
    private Date joinDate;

    //@JsonIgnore //json에서 무시됨. 즉 클라이언트에게 json 문서를 줄 때 해당 필드가 안보인다.
    private String password;
    //@JsonIgnore
    private String ssn;




}
