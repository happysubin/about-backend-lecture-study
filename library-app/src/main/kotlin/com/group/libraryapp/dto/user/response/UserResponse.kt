package com.group.libraryapp.dto.user.response

import com.group.libraryapp.domain.user.User

data class UserResponse(
    val id: Long,
    val name: String,
    val age: Int?
) {

    companion object {
        fun of(user: User): UserResponse {
            return UserResponse(
                id = user.id !!,
                //user.id!!는 널 단정 연산자로, user.id가 널이 아님을 확실히 가정합니다.
                // 그러나 만약 user.id가 널이면 NullPointerException이 발생합니다.
                name = user.name,
                age = user.age
            )
        }
    }
}