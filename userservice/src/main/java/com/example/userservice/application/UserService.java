package com.example.userservice.application;

import com.example.userservice.domain.User;
import com.example.userservice.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto registerUser(UserRequestDto userDto){
        User user = userDto.toEntity();
        userRepository.save(user);
        return UserResponseDto.of(user);
    }
}
