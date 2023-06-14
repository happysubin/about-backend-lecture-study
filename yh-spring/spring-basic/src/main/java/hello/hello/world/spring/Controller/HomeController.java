package hello.hello.world.spring.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(){
        return "home";
    }
}

//controller이 컨트롤러가 정적 파일보다 우선순위가 높다.