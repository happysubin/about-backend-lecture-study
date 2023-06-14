package hello.hello.world.spring.domain;

import javax.persistence.*;

@Entity //이 어노테이션을 통해 jpa가 관리하는 엔티티라고 표현한다.
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)//DB가 알아서 id를 생성
    private Long id;

    //@Column(name="username")
    private String name;

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id=id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
}
