package hello.itemservice.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor //JPA는 public, protected 생성자가 필수. 동적 프록시 기술을 사용하기 위해
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_name", length = 10) //캐멀 케이스와 사실 스네이크 케이스를 저절로 바꿔준다
    private String itemName;
    private Integer price;
    private Integer quantity;


    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
