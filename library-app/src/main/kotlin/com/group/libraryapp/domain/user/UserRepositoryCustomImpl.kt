package com.group.libraryapp.domain.user

import com.group.libraryapp.domain.user.QUser.*
import com.group.libraryapp.domain.user.loanhistory.QUserLoanHistory
import com.group.libraryapp.domain.user.loanhistory.QUserLoanHistory.*
import com.querydsl.core.QueryFactory
import com.querydsl.jpa.impl.JPAQueryFactory

class UserRepositoryCustomImpl(
    private val jpaQueryFactory: JPAQueryFactory
): UserRepositoryCustom {
    override fun findAllWithHistories(): List<User> {
        return jpaQueryFactory
            .select(user)
            .distinct()
            .from(user)
            .leftJoin(userLoanHistory).on(userLoanHistory.user.id.eq(user.id))
            .fetchJoin()
            .fetch();
    }
}