package hello.hello.world.spring.Controller;


import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @GetMapping("hello") //get method 로 /hello 로 요청!
    public String hello(Model model){ //spring이 모델을 하나 준다
        model.addAttribute("data","hello"); //모델에 key는 data value는 hello를 추가
        return "hello"; //템플릿 dir 아래에 hello라는 이름을 가진 html 파일을 렌더링한다.
        //그러면 렌더링할 hello.html로 가서 data 라는 이름을 보면 data key 값이 value 로 치환되어 hello가 출력된다.
    }
    //mvc
    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name,Model model){
        model.addAttribute("name",name);
        return "hello-mvc";
        //http://localhost:8080/hello-mvc?name=33 이 url로 가면 정상적으로 페이지가 렌더링된다.
    }
    //api
    @GetMapping("hello-string")
    @ResponseBody //http body에다가 내용을 담겠다는 뜻이다. 즉 리턴하는 내용을 http body에 담겠다는 의미
    public String string (@RequestParam("name")String name){
        return "hello"+name;
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi (@RequestParam("name")String name){
        Hello hello=new Hello();
        hello.setName(name); //파라미터로 온 name이 들어간다
        return hello;
    }

    static class Hello{
        private String name;
        public String getName(){
            return name;
        }
        public void setName(String name){
            this.name=name;
        }
    }
}
