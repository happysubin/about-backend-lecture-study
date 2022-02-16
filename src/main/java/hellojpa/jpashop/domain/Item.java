package hellojpa.jpashop.domain;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)//싱글 테이블 전략
@DiscriminatorColumn(name="DTYPE")//기본이 DTYPE
public abstract class Item extends BaseEntity{

    @Id @GeneratedValue
    @Column(name="ITEM_ID")
    private Long id;

    @ManyToMany(mappedBy = "items")
    private List<Category> catogries=new ArrayList<>();

    private String name;
    private int price;
    private int stockQuantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}
