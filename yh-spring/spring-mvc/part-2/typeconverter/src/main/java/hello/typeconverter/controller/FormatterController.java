package hello.typeconverter.controller;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
public class FormatterController {

    @GetMapping("/formatter/edit")
    public String formatterForm(Model model) {
        Form form = new Form();
        form.setNumber(10000);
        form.setLocalDateTime(LocalDateTime.now());
        model.addAttribute("form", form);
        return "formatter-form";
    }
    @PostMapping("/formatter/edit")
    public String formatterEdit(@ModelAttribute Form form) {
        return "formatter-view";
    }

    @Data
    static class Form {
        @NumberFormat(pattern = "###,###") //숫자 관련 형식 지정 포맷터
        private Integer number;
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//날짜 관련 형식 지정 포맷터 사용. 참고로 이게 국제 표준
        private LocalDateTime localDateTime;
    }
}
