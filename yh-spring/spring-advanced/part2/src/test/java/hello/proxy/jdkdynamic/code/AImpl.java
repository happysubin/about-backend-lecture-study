package hello.proxy.jdkdynamic.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AImpl implements AInterface{
    @Override
    public String calls() {
        log.info("A 호출");
        return "a";
    }

    @Override
    public String calls2() {
        log.info("살려줘");
        return "22";
    }
}
