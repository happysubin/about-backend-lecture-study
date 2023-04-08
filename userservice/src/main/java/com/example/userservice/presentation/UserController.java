package com.example.userservice.presentation;

import com.example.userservice.application.UserRequestDto;
import com.example.userservice.application.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/first-service")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public String createUser(@RequestBody RequestUser request){
        UserRequestDto userDto= request.toDto();
        userService.registerUser(userDto);
        return "create user method is called";
    }

//    @GetMapping("/message")
//    public String message (@RequestHeader("first-request") String header){
//        System.out.println("header = " + header);
//        return header;
//    }
//
//    @GetMapping("/check")
//    public String check (HttpServletRequest request){
//        return "check " + request.getServerPort();
//    }
}
