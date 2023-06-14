package com.example.userservice.security;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@Getter
public class RequestLogin {

    @Email
    private String email;

    @NotEmpty
    private String password;
}
