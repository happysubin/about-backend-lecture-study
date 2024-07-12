package com.lecture.security.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain


@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeHttpRequests { auth -> auth.anyRequest().authenticated() }
            .formLogin(Customizer.withDefaults())
        return http.build()
    }

    @Bean
    fun inMemoryUserDetailsManager(): InMemoryUserDetailsManager {
        val user: UserDetails = User
            .withUsername("root")
            .password("{noop}1234")
            .authorities("ROLE_ADMIN")
            .build()
        return InMemoryUserDetailsManager(user)
    }
}