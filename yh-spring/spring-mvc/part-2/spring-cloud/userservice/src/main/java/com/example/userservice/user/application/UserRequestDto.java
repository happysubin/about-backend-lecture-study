package com.example.userservice.user.application;

import com.example.userservice.user.domain.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Getter
@NoArgsConstructor
public class UserRequestDto {

    private String email;
    private String name;
    private String userId;
    private String encryptedPwd;

    public UserRequestDto(String email, String name, String encryptedPwd) {
        this.email = email;
        this.name = name;
        this.userId = UUID.randomUUID().toString();
        this.encryptedPwd = encryptedPwd;
    }

    public Users toEntity() {
        return Users.builder()
                .userId(userId)
                .email(email)
                .encryptedPwd(encryptedPwd)
                .name(name)
                .build();
    }
}
