package com.example.userservice.presentation;

import com.example.userservice.application.UserRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestUser {
    
    private String email;
    private String name;
    private String pwd;

    public UserRequestDto toDto() {
        return new UserRequestDto(email, name, pwd);
    }
}
