package com.example.userservice.application;

import com.example.userservice.domain.User;
import com.example.userservice.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.RuntimeOperationsException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto registerUser(UserRequestDto userDto){
        User user = userDto.toEntity();
        userRepository.save(user);
        return UserResponseDto.of(user);
    }

    public UserResponseDto getUserById(String userId){
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("멤버가 없습니다"));
        return UserResponseDto.of(user);
    }

    public List<UserResponseDto> getUserByAll(){
        return userRepository.findAll()
                .stream()
                .map(user -> UserResponseDto.of(user))
                .collect(Collectors.toList());
    }
}
