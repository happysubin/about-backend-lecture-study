package hello.hello.world.spring.Controller;


import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("hello") //get method 로 /hello 로 요청!
    public String hello(Model model){ //spring이 모델을 하나 준다
        model.addAttribute("data","hello"); //모델에 key는 data value는 hello를 추가
        return "hello"; //템플릿 dir 아래에 hello라는 이름을 가진 html 파일을 렌더링한다.
        //그러면 렌더링할 hello.html로 가서 data 라는 이름을 보면 data key 값이 value 로 치환되어 hello가 출력된다.
    }
}
