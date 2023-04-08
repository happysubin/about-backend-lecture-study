package com.example.userservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/first-service")
public class UserController {

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/message")
    public String message (@RequestHeader("first-request") String header){
        System.out.println("header = " + header);
        return header;
    }

    @GetMapping("/check")
    public String check (){
        return "check";
    }
}
