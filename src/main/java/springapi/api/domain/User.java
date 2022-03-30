package springapi.api.domain;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;


@Data
@AllArgsConstructor
//@JsonIgnoreProperties(value = {"password"}) //password만 제외딘다. 클래스 상단에서 사용
@NoArgsConstructor
//@JsonFilter("UserInfo")
@ApiModel(description = "사용자 상세 정보를 위한 도메인 객체")
public class User {
    private Long id;

    @Size(min=2,message = "Name는 2글자 이상 입력하세요") //한 글자만 넣으면 상태코드 400이 리턴증
    @ApiModelProperty(notes = "사용자 이름을 입력하시오")
    private String name;

    @Past// 과거 데이터만 사용 가능
    @ApiModelProperty(notes = "사용자의 등록일을 입력하시오")
    private Date joinDate;

    //@JsonIgnore //json에서 무시됨. 즉 클라이언트에게 json 문서를 줄 때 해당 필드가 안보인다.
    @ApiModelProperty(notes = "사용자 비밀번호을 입력하시오")
    private String password;
    //@JsonIgnore
    @ApiModelProperty(notes = "사용자 주민번호을 입력하시오")
    private String ssn;




}
