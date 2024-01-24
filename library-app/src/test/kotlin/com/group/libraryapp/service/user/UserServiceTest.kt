package com.group.libraryapp.service.user

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import javax.transaction.Transactional

@SpringBootTest

class UserServiceTest @Autowired constructor(
    //@Autowired private val userRepository: UserRepository,
    //@Autowired private val userService: UserService
     private val userRepository: UserRepository,
     private val userService: UserService
) {

    //@Transactional
    @Test
    @DisplayName("유저 저장이 정상 동작한다")
    fun saveUserTest() {

        val request = UserCreateRequest("subin", null)

        userService.saveUser(request)

        val results = userRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo("subin")
        assertThat(results[0].age).isNull()
    }

    //@Transactional
    @Test
    @DisplayName("유저 조회가 정상 동작한다")
    fun getUsersTest() {

        userRepository.saveAll(
            listOf(
                User("su", 20),
                User("bin", 21)
            )
        )

        val results = userRepository.findAll()

        assertThat(results).hasSize(2)
        assertThat(results).extracting("name").containsExactlyInAnyOrder("bin", "su")
        assertThat(results).extracting("age").containsExactlyInAnyOrder(20, 21)
    }

    @Test
    @DisplayName("유저 이름 수정이 동작한다")
    fun updateUserNameTest() {
        val savedUser = userRepository.save(User("A", 10))
        val request = UserUpdateRequest(savedUser.id, "B")

        userService.updateUserName(request)

        val result = userRepository.findAll()[0]
        assertThat(result.name).isEqualTo("B")
    }

    @Test
    @DisplayName("유저 삭제가 정상 동작한다")
    fun deleteUserTest() {
        userRepository.save(User("A", 20))

        userService.deleteUser("A")

        assertThat(userRepository.findAll()).hasSize(0)
    }

    @AfterEach
    fun afterEach() {
        userRepository.deleteAll()
    }
}