package com.lecture.security.section06

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain


@EnableWebSecurity
@Configuration
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {

        http
            .authorizeHttpRequests {
                auth -> auth
                    .requestMatchers("/login").permitAll()
                    .requestMatchers("/admin").hasRole("ADMIN")
                    .anyRequest().authenticated()
            }
            .formLogin(Customizer.withDefaults())
            .exceptionHandling { exception ->
            exception
//                .authenticationEntryPoint { request, response, authException ->
//                    println("entryPoint: " + authException.message)
//                    response.sendRedirect("/login")
//                }
                //사용자 정의 AuthenticationEntryPoint 구현이 가장 우선적으로 수행되며 이때는 기본 로그인 페이지 생성이 무시된다
                .accessDeniedHandler { request, response, accessDeniedException ->  // 커스텀하게 사용할 AccessDeniedHandler 를 설정한다
                    println("accessDenied: " + accessDeniedException.message)
                    response.sendRedirect("/denied")
                }
        }


        return http.build()
    }

    @Bean
    fun inMemoryUserDetailsManager(): UserDetailsService {
        val user: UserDetails = User
            .withUsername("root")
            .password("{noop}1234")
            .authorities("ROLE_USER")
            .build()
        return InMemoryUserDetailsManager(user)
    }
}