package com.lecture.security.section2.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
//@Configuration(proxyBeanMethods = false)
@Configuration
class SecurityConfig {

//    @Bean
    fun securityFilterChainFormLogin(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { auth -> auth.requestMatchers("/error").permitAll().anyRequest().authenticated() }
            .formLogin { httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer
//                .loginPage("/loginPage") // 사용자 정의 로그인 페이지로 전환, 기본 로그인페이지 무시
                .loginProcessingUrl("/loginProc") //사용자 이름과 비밀번호를 검증할 URL 지정 (Form action)
                .defaultSuccessUrl("/", false) // 로그인 성공 이후 이동 페이지, alwaysUse 가 true 이면 무조건 지정된 위치로 이동(기본은 false). 인증 전에 보안이 필요한 페이지를 방문하다가 인증에 성공한 경우이면 이전 위치로 리다이렉트 됨
                .failureUrl("/failed") // 인증에 실패할 경우 사용자에게 보내질 URL 을 지정, 기본값은 "/login?error" 이다
                .usernameParameter("username")
                .passwordParameter("password")
                .failureHandler { request, response, exception ->
                    println("error")
                    response.sendRedirect("/error")
                }
                .successHandler { request, response, authentication ->
                    println("success")
                    response.sendRedirect("/home")
                }
                .permitAll() //// failureUrl(), loginPage(), loginProcessingUrl() 에 대한 URL 에 모든 사용자의 접근을 허용 함
            }

        return http.build()
    }

//    @Bean
    fun securityFilterChainHttpBasic(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { auth -> auth.anyRequest().authenticated() }
            .httpBasic {httpSecurityHttpBasicConfigurer -> httpSecurityHttpBasicConfigurer
            .realmName("securty")
            .authenticationEntryPoint{ request, response, authException ->
                response.addHeader("WWW-Authenticate", "Basic realm=localhost");
                response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
            }
        }

        return http.build()
    }

//    @Bean
    fun securityFilterChainRememberMe(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { auth -> auth.anyRequest().authenticated() }
            .formLogin { Customizer.withDefaults<FormLoginConfigurer<HttpSecurity>>()  }
            .rememberMe { httpSecurityRememberMeConfigurer ->
                httpSecurityRememberMeConfigurer
                    .alwaysRemember(true)
                    .tokenValiditySeconds(3600)
                    .userDetailsService(inMemoryUserDetailsManager())
                    .rememberMeParameter("remember") /// 로그인 시 사용자를 기억하기 위해 사용되는 HTTP 매개변수이며 기본값은 'remember-me' 이다
                    .rememberMeCookieName("remember") //// 기억하기(remember-me) 인증을 위한 토큰을 저장하는 쿠키 이름이며 기본값은 'remember-me' 이다
                    .key("security")
            }

        return http.build()
    }

    @Bean
    fun securityFilterChainAnonymous(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { auth -> auth
                .requestMatchers("/anonymous").hasRole("GUEST")
                .requestMatchers("/anonymousContext", "/authentication").permitAll()
                .anyRequest()
                .authenticated()

            }
            .formLogin { Customizer.withDefaults<FormLoginConfigurer<HttpSecurity>>()  }
            .anonymous { httpSecurityAnonymous ->
                httpSecurityAnonymous
                    .principal("guest")
                    .authorities("ROLE_GUEST")
            }

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