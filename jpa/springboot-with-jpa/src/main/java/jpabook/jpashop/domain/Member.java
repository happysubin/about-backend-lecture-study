package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name="member_id")
    private Long id;

    //@UniqueElements
    private String name;

    @Embedded
    private Address address;

    //@JsonIgnore DTO를 사용하면 불필요하다.
    @OneToMany(mappedBy = "member")
    private List<Order> orders=new ArrayList<>();

}
