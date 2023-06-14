package hello.advanced.trace.threadlocal.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadLocalService {

    private ThreadLocal<String> nameStore = new ThreadLocal<>();

    public String logic(String name){
        log.info("저장 name={} -> nameStore={}", name, nameStore);
        nameStore.set(name);
        sleep(1000);
        log.info("조회 nameStore={}",nameStore);
        return nameStore.get();
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
//기존에 있던 FieldService 와 거의 같은 코드인데, nameStore 필드가 일반 String 타입에서 ThreadLocal 을 사용하도록 변경되었다.

//해당 쓰레드가 쓰레드 로컬을 모두 사용하고 나면 ThreadLocal.remove() 를 호출해서 쓰레드 로컬에
//저장된 값을 제거해주어야 한다. 제거하는 구체적인 예제는 조금 뒤에 설명하겠다.