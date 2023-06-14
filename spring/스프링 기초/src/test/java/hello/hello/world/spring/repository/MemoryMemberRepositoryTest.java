package hello.hello.world.spring.repository;

//import org.junit.jupiter.api.Assertions; 값을 비교
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test; //테스트 코드를 위한 라이브러리
import hello.hello.world.spring.domain.Member;
import java.util.List;
import java.util.Optional;

//다른곳에서 안쓰니 public 할 필요 없다.
//테스트코드 관례는 클래스 이름 마지막에 Test 만 붙인다.


 class MemoryMemberRepositoryTest {
  MemoryMemberRepository repository=new MemoryMemberRepository();
  //원래자료형을 인터페이스로 구현했다가 취소 그냥 클래스로. clearStore를 사용하기 위해서

  @AfterEach //어떤 메소드가 끝나면 실행된다. 콜백 메소드 밑에 메소드가 하나 끝나면 호출된다.
  public void afterEach(){
   repository.clearStore(); //이러면 각각의 메소드가 끝나면 매번 실행된다.
  }

  @Test
  public void save(){
   Member member=new Member();
   member.setName("Spring");

   repository.save(member);
   Member result=repository.findById(member.getId()).get();
   //optional에서 값을 꺼낼 때는 get이라는 메소드를 이용! 좋은 방법은 아님 테스트 코드에서 이용
   System.out.println("result = "+(result == member));
   //result = true 라고 출력된다. 이 방법도 좋지만 Assertions라는 기능이 있다.
   //Assertions.assertEquals(result,member); //result 와 member 의 값을 비교
   //Assertions.assertEquals(member,null); 에러가 출력되는걸 확인 가능
   Assertions.assertThat(member).isEqualTo(result);//이게 제일 편하다
  }
  @Test
  public void findByName(){
   Member member1=new Member();
   member1.setName("spring1");
   repository.save(member1);

   //shitf F6으로 복붙하고 이름 싹다 바꾸기 가능 꿀팁
   Member member2=new Member();
   member2.setName("spring2");
   repository.save(member2);

   //Optional<Member> result =repository.findByName("spring1");
   Member result= repository.findByName("spring1").get(); // get 메소드를 이용해 위처럼 작성하지 않아도 된다
   Assertions.assertThat(result).isEqualTo(member1);
  }

  @Test
  public void findAll(){
   Member member1=new Member();
   member1.setName("spring1");
   repository.save(member1);

   Member member2=new Member();
   member2.setName("spring1");
   repository.save(member2);

   List<Member> result=repository.findAll();
   Assertions.assertThat(result.size()).isEqualTo(2);
  }
}

//테스트 코드는 순서에 의존적으로 설계하면 안된다.
//현재 member1 member2를 각각 사용하는데 테스트를 하나 끝내고 나면 이전에 저장한 데이터가 남아있어서 오류가 발생한다.
//따라서 데이터를 클리어 시켜줘야한다. 협업시 테스트코드는 정말 중요! 깊이 있게 공부해야한다.

//지금은 개발을 하고 테스트 코드 진행
//반대로 테스트코드를 작성하고 개발을 진행하는 역순의 방식을 TDD라고 부른다.