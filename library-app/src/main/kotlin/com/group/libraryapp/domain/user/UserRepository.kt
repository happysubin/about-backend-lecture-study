package com.group.libraryapp.domain.user

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, Long> {

    //fun findByName(nameP: String): Optional<User>
    fun findByName(nameP: String): User? //스프링이 알아서 null을 넣어줌.

}