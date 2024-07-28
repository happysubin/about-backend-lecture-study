package com.lecture.security.section04

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


//    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {

        val authenticationManagerBuilder = http.getSharedObject(
            AuthenticationManagerBuilder::class.java
        )
        val authenticationManager = authenticationManagerBuilder.build()

        http
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/api/login").permitAll()
                    .anyRequest().authenticated()
            }
            .formLogin(Customizer.withDefaults())
            .securityContext { securityContext: SecurityContextConfigurer<HttpSecurity?> ->
                securityContext
                    .requireExplicitSave(true) //false -> SecurityContextPersistenceFilter를 사용하도록 설정함. 우리가 명시적으로 시큐리티 컨텍스트를 저장하지 않아도 됨.
            }
            .authenticationManager(authenticationManager)
            .addFilterBefore(customFilter(http, authenticationManager), UsernamePasswordAuthenticationFilter::class.java)


        return http.build()
    }


    @Bean
    fun securityFilterChainMvcLogin(http: HttpSecurity): SecurityFilterChain {

        http
            .authorizeHttpRequests { auth -> auth
                .requestMatchers("/login").permitAll()
                .anyRequest().authenticated()
            }
//            .formLogin(Customizer.withDefaults()) 폼로그인 사용 안함
            .csrf { obj: AbstractHttpConfigurer<*, *> -> obj.disable() }

        return http.build()
    }

    fun customFilter(http: HttpSecurity, authenticationManager: AuthenticationManager): CustomAuthenticationFilter {
        val customAuthenticationFilter = CustomAuthenticationFilter(http)
        customAuthenticationFilter.setAuthenticationManager(authenticationManager)
        return customAuthenticationFilter
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

//    @Bean
//    fun authenticationManager(builder: AuthenticationManagerBuilder): AuthenticationManager {
//        println(builder)
//        println("hihihih")
//        builder.authenticationProvider(DaoAuthenticationProvider())
//        builder!!.build()
//        return builder.`object`
//    }



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