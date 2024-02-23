package com.group.libraryapp.domain.user.loanhistory

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserLoanHistoryRepository : JpaRepository<UserLoanHistory, Long> {
    fun findByBookNameAndStatus(bookName: String, status: UserLoanStatus) : UserLoanHistory?

}