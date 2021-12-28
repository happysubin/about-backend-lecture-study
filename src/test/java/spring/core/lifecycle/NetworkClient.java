package spring.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class NetworkClient implements InitializingBean, DisposableBean {
    private String url;

    public NetworkClient() {
        System.out.println("생성차 호출, url = " + url);
        
    }

    //서비스 시작 시 호출
    public void connect(){
        System.out.println("connect = " + url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void call (String message){
        System.out.println("call = "+ url + "message = " + message);
    }

    //서비스 종료 시 호출
    public void disconnect(){
        System.out.println("close = " + url);
    }

    @Override// 의존관계 주입이 끝나면  이 메소드가 호출 InitializingBean 은 afterPropertiesSet() 메서드로 초기화를 지원한다
    public void afterPropertiesSet() throws Exception {
        //프로퍼티 셋팅이 끝나면 -> 의존 관계 주입이 끝나면 호출한다는 뜻의 이름
        System.out.println("NetworkClient.afterPropertiesSet");
        connect();
        call("초가화 연결 메시지");
        //그러므로 이것을 옮긴다.
    }

    @Override //DisposableBean 은 destroy() 메서드로 소멸을 지원한다.
    public void destroy() throws Exception {
        disconnect();
    }

}


/**
 * 초기화, 소멸 인터페이스 단점
 * 이 인터페이스는 스프링 전용 인터페이스다. 해당 코드가 스프링 전용 인터페이스에 의존한다.
 * 초기화, 소멸 메서드의 이름을 변경할 수 없다.
 * 내가 코드를 고칠 수 없는 외부 라이브러리에 적용할 수 없다.
 *
 * > 참고: 인터페이스를 사용하는 초기화, 종료 방법은 스프링 초창기에 나온 방법들이고, 지금은 다음의 더
 * 나은 방법들이 있어서 거의 사용하지 않는다.
 */