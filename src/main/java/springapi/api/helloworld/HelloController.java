package springapi.api.helloworld;

import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloController {

    @Autowired
    private MessageSource messageSource;



    @GetMapping(path="/hello-world")
    public String hello(){
        return "Hello World";
    }

    @GetMapping("/hello-world2")
    public HelloWorldBean helloWorldBean(){
        return new HelloWorldBean("hello world bean"); //REST controller을 쓰면 json으로 반환해준다.
    }

    @GetMapping("/hello-world/path-variable/{id}")
    public HelloWorldBean pathVariable(@PathVariable String id){
        System.out.println("id = " + id);
        return new HelloWorldBean("hello world bean "+id); //REST controller을 쓰면 json으로 반환해준다.
    }

    @GetMapping("/hello-world-internationalized") //헤더에 따라 리턴하는 메시지가 달라진다
    public String helloWorldInternationalized(@RequestHeader(value = "Accept-Language",required = false) Locale locale ){
            return messageSource.getMessage("greeting.message",null,locale);
    }

}
