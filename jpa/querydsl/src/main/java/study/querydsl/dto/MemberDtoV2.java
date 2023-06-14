package study.querydsl.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberDtoV2 {

    private String username;
    private int age;

    @QueryProjection //gradle에 들어가서 compileJava를 한다.
    public MemberDtoV2(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
