package com.example.userservice.application;


import com.example.userservice.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class UserResponseDto {

    private String email;
    private String name;
    private String userId;

    private List<ResponseOrder> orders;

    public static UserResponseDto of (User user){
        return new UserResponseDto(user.getEmail(), user.getName(), user.getUserId(), new ArrayList<>());
    }

    public UserResponseDto(String email, String name, String userId, List<ResponseOrder> orders) {
        this.email = email;
        this.name = name;
        this.userId = userId;
        this.orders = orders;
    }
}
