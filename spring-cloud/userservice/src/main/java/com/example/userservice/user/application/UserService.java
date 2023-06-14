package com.example.userservice.user.application;

import com.example.userservice.user.domain.Users;
import com.example.userservice.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserResponseDto registerUser(UserRequestDto userDto){
        Users user = userDto.toEntity();
        userRepository.save(user);
        return UserResponseDto.of(user);
    }

    public UserResponseDto getUserById(String userId){
        Users user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("멤버가 없습니다"));
        return UserResponseDto.of(user);
    }

    public List<UserResponseDto> getUserByAll(){
        return userRepository.findAll()
                .stream()
                .map(user -> UserResponseDto.of(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));



        return new User(
                user.getEmail(),
                user.getEncryptedPwd(),
                true,
                true,
                true,
                true,
                new ArrayList<>()
        );
    }

    public String getUserDetailsByEmail(String email) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));

        return user.getUserId();
    }
}
