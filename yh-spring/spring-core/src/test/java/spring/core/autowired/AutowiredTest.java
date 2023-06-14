package spring.core.autowired;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;
import spring.core.member.Member;

import java.util.Optional;

public class AutowiredTest {

    @Test
    void AutowiredOption(){
        AnnotationConfigApplicationContext ac=new AnnotationConfigApplicationContext(TestBean.class);

    }

    static class TestBean{
        
        //메서드 자체가 호출 안됨
        @Autowired(required = false) //기본값은 true다. 만약 true 면 멤버가 빈이 아니므로 오류가 터진다.
        public void setNoBean1(Member member){
            System.out.println("member = " + member);
        }

        @Autowired
        public void setNoBean2(@Nullable Member member){ //@Nullable 사용
            System.out.println("member = " + member);
        }

        @Autowired
        public void setNoBean3(Optional<Member> noBean3){ //옵셔널 문법 사용
            System.out.println("noBean3 = " + noBean3);
        }
    }
}


/**
 * 주입할 스프링 빈이 없어도 동작해야 할 때가 있다.
 * 그런데 @Autowired 만 사용하면 required 옵션의 기본값이 true 로 되어 있어서 자동 주입 대상이
 * 없으면 오류가 발생한다. 자동 주입 대상을 옵션으로 처리하는 방법은 위 3가지 메소드와 같다.
 *
 * 출력 값
 * member = null
 * noBean3 = Optional.empty 자바 8에서 지원하는 Optional 문법
 */