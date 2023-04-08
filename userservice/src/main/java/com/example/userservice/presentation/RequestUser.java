package com.example.userservice.presentation;

import com.example.userservice.application.UserRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor
public class RequestUser {
    
    private String email;
    private String name;
    private String pwd;

    public UserRequestDto toDto(PasswordEncoder passwordEncoder) {
        return new UserRequestDto(email, name, passwordEncoder.encode(pwd));
    }
}
