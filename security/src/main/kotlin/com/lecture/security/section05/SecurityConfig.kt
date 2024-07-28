package com.lecture.security.section05

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.config.annotation.web.configurers.SecurityContextConfigurer
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@EnableWebSecurity
@Configuration
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {


    http.sessionManagement { session ->
        session
            .invalidSessionUrl("/invalidSessionUrl") // 이미 만료된 세션으로 요청을 하는 사용자를 특정 엔드포인트로 리다이렉션 할 Url 을 지정
            .maximumSessions(1) // 사용자당 최대 세션 수를 제어한다. 기본값은 무제한 세션을 허용한다. 이게 젤 중요!
            .maxSessionsPreventsLogin(false) // true 이면 최대 세션 수 (maximumSessions(int))에 도달했을 때 사용자의 인증을 방지한다
                                            // false(기본 설정)이면 인증하는 사용자에게 접근을 허용하고 기존 사용자의 세션은 만료된다
            .expiredUrl("/expired") // 세션을 만료하고 나서 리다이렉션 할 URL 을 지정한다
    }

        //사용자 세션 강제 만료에서 invalidSessionUrl과 expiredUrl이 관련있다.
        //사용자 인증 시도 차단에서는 의미 X.
        // invalidSessionUrl과 expiredUrl 둘 중 우선순위는 invalidSessionUrl이 더 높다


    return http.build()
    }



    @Bean
    fun inMemoryUserDetailsManager(): UserDetailsService {
        val user: UserDetails = User
            .withUsername("root")
            .password("{noop}1234")
            .authorities("ROLE_ADMIN")
            .build()
        return InMemoryUserDetailsManager(user)
    }
}