package com.lecture.security.section03

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.context.DelegatingSecurityContextRepository
import org.springframework.security.web.context.HttpSessionSecurityContextRepository
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository
import org.springframework.security.web.context.SecurityContextRepository
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

class CustomAuthenticationFilter(
    http: HttpSecurity
) : AbstractAuthenticationProcessingFilter(AntPathRequestMatcher("/api/login", "GET")) {

    private val objectMapper = ObjectMapper()

    init {
        setSecurityContextRepository(getSecurityContextRepository(http))
    }


    //이걸 세팅했으므로 명시적으로 세션을 저장하는 것
    private fun getSecurityContextRepository(http: HttpSecurity): SecurityContextRepository {
        var securityContextRepository = http.getSharedObject(SecurityContextRepository::class.java)

        if (securityContextRepository == null) {
            securityContextRepository = DelegatingSecurityContextRepository(
                HttpSessionSecurityContextRepository(),
                RequestAttributeSecurityContextRepository()
            )
        }
        return securityContextRepository
    }


    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        val username = request.getParameter("username")
        val password = request.getParameter("password")

        val token = UsernamePasswordAuthenticationToken(username, password)

        return authenticationManager.authenticate(token)
    }
}