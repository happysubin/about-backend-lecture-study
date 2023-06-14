package com.example.userservice.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

    @Query("select u from Users u where u.userId = :userId")
    Optional<Users> findByUserId(String userId);

    @Query("select u from Users u where u.email = :email")
    Optional<Users> findByEmail(String email);
}
