package com.group.libraryapp.service.user

import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.user.request.UserCreateRequest
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest

class UserServiceTest @Autowired constructor(
    //@Autowired private val userRepository: UserRepository,
    //@Autowired private val userService: UserService
     private val userRepository: UserRepository,
     private val userService: UserService
) {

    @Test
    fun saveUserTest() {

        val request = UserCreateRequest("subin", null)

        userService.saveUser(request)

        val results = userRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo("subin")
        assertThat(results[0].age).isNull()
    }


}