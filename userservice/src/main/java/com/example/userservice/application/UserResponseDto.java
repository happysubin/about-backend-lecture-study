package com.example.userservice.application;


import com.example.userservice.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {

    private String email;
    private String name;
    private String userId;

    public static UserResponseDto of (User user){
        return new UserResponseDto(user.getEmail(), user.getName(), user.getUserId());
    }

    public UserResponseDto(String email, String name, String userId) {
        this.email = email;
        this.name = name;
        this.userId = userId;
    }
}
