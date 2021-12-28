package spring.core.lifecycle;

public class NetworkClient {
    private String url;

    public NetworkClient() {
        System.out.println("생성차 호출, url = " + url);
        connect();
        call("초가화 연결 메시지");
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
}


//로그에 출력만