package hellojpa;




import javax.persistence.*;
import java.util.Date;

@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.AUTO) //이게 기본
    //GenerationType.IDENTITY 이건 DB에게 기본 키 생성을 위임. 주로 MySQL, PostgreSQL, SQL Server, DB2에서 사용 
    //대신 이론상 이건 DB에 커밋되야지 key가 생김. 근데 영속성 컨텍스트에서는 아직 커밋되기 전이다. 따라서 키가 없어!!! but Identity는 예외적으로
    //em.persist 메소드를 사용하면 insert 쿼리가 날라간다. 그래서 커밋전에 key를 받을 수 있다.

    //GenerationType.SEQUENCE 데이터베이스 시퀀스는 유일한 값을 순서대로 생성하는 특별한 데이터베이스 오브젝트 오라클, PostgreSQL, DB2, H2 데이터베이스에서 사용
    //SEQUENCE는 em.persist 시점에 DB에서 키를 가져온다. 이 값을 얻어서 엔티티에 넣고 엔티티를 영속성 컨텍스트에 넣어버린다.
    private Long id; //무조건 Long 사용하자.

    @Column(name="name")
    private String name;

    private String username;

    private Integer age;

    @Enumerated(EnumType.STRING) //enum을 사용. String 으로 무조건 사용!!!
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP) //날짜,시간 타입 사용
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob //큰 문자열을 넣고 싶다면 사용
    private String description;

    public Member() {

    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
