package spring.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Component
//이렇게 하면 MyLogger의 가짜 프록시 클래스를 만들어두고 HTTP request와 상관 없이 가짜 프록시 클래스를 다른 빈에 미리 주입해 둘 수 있다
//적용 대상이 인터페이스가 아닌 클래스면 TARGET_CLASS 를 선택
//적용 대상이 인터페이스면 INTERFACES 를 선택
@Scope(value="request",proxyMode = ScopedProxyMode.TARGET_CLASS)
public class  MyLogger {
    private  String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message){
        System.out.println("[" + uuid + "]" + "[" + requestURL + "] " + message);
    }

    @PostConstruct
    public void init(){
        uuid=UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] request scope bean create:" + this);
    }

    @PreDestroy //이거 호출됨. 프로토타입과는 다르다.
    public void close(){
        System.out.println("[" + uuid + "] request scope bean close:" + this);
    }
}

/**
 * 로그를 출력하기 위한 MyLogger 클래스이다.
 * @Scope(value = "request") 를 사용해서 request 스코프로 지정했다. 이제 이 빈은 HTTP 요청 당
 * 하나씩 생성되고, HTTP 요청이 끝나는 시점에 소멸된다.
 * 이 빈이 생성되는 시점에 자동으로 @PostConstruct 초기화 메서드를 사용해서 uuid를 생성해서저장해둔다.
 * 이 빈은 HTTP 요청 당 하나씩 생성되므로, uuid를 저장해두면 다른 HTTP 요청과 구분할 수 있다.
 * 이 빈이 소멸되는 시점에 @PreDestroy 를 사용해서 종료 메시지를 남긴다.
 * requestURL 은 이 빈이 생성되는 시점에는 알 수 없으므로, 외부에서 setter로 입력 받는다.
 *
 */

/**
 *
 * 스프링 애플리케이션을 실행 시키면 오류가 발생한다. 메시지 마지막에 싱글톤이라는 단어가 나오고…
 * 스프링 애플리케이션을 실행하는 시점에 싱글톤 빈은 생성해서 주입이 가능하지만,
 * request 스코프 빈은 아직 생성되지 않는다. 이 빈은 실제 고객의 요청이 와야 생성할 수 있다!
 * 즉 요청이 없는데 등록시키니까 오류가 터지는 것것 */