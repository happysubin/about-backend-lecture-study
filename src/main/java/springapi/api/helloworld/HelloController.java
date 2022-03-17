package springapi.api.helloworld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

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

}
