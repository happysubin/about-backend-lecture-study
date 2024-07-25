package com.lecture.security.section2.config

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.security.web.savedrequest.HttpSessionRequestCache
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

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

//    @Bean
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

//    @Bean
    fun securityFilterChainLogout(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { auth -> auth
                .requestMatchers("/logoutSuccess").permitAll()
                .anyRequest()
                .authenticated()
            }
            .formLogin { Customizer.withDefaults<FormLoginConfigurer<HttpSecurity>>()  }
//            .csrf{ csrf -> csrf.disable() }
            .logout() { httpSecurityLogout ->
                httpSecurityLogout
                    .logoutUrl("/logoutProc")
//                    .logoutRequestMatcher(AntPathRequestMatcher("/logoutProc", "POST"))
                    .logoutRequestMatcher(AntPathRequestMatcher("/logoutProc"))// / 로그아웃이 발생하는 RequestMatcher 을 지정한다. logoutUrl 보다 우선적
                    .logoutSuccessUrl("/logoutSuccess")
                    .logoutSuccessHandler(object: LogoutSuccessHandler {
                        override fun onLogoutSuccess(
                            request: HttpServletRequest?,
                            response: HttpServletResponse?,
                            authentication: Authentication?
                        ) {
                            response?.sendRedirect("/logoutSuccess")
                        }
                    })
                    .deleteCookies("JSESSIONID", "remember-me")
                    .invalidateHttpSession(true) //HttpSession을 무효화해야 하는 경우 true (기본값), 그렇지 않으면 false
                    .clearAuthentication(true) // 로그아웃 시 SecurityContextLogoutHandler가 인증(Authentication)을 삭제 해야 하는지 여부를 명시
                    .addLogoutHandler { request, response, authentication ->
                        val session = request.session
                        session.invalidate()
                        SecurityContextHolder.getContextHolderStrategy().context.authentication = null
                        SecurityContextHolder.getContextHolderStrategy().clearContext()
                    }  //// 기존의 로그아웃 핸들러 뒤에 새로운 LogoutHandler를 추가
                    .permitAll()
            }

        return http.build()
    }

    @Bean
    fun securityFilterChainSavedRequestAndRequestCache(http: HttpSecurity): SecurityFilterChain {
        val requestCache = HttpSessionRequestCache()
        //요청 Url 에 customParam=y 라는 이름의 매개 변수가 있는 경우에만 HttpSession 에 저장된 SavedRequest 을 꺼내오도록 설정할 수 있다 (기본값은 "continue")
        //이게 필요한 이유는 특정한 상황에만 적용하기 위해.
        requestCache.setMatchingRequestParameterName("customParam=y")  // 요청이 성공하면 쿼리스트링으로 뒤에 이게 붙음.

        http
            .authorizeHttpRequests { auth -> auth.anyRequest().authenticated() }
            .formLogin { formLogin -> formLogin
                .successHandler { request, response, authentication ->
                    val savedRequest = requestCache.getRequest(request, response)
                    val redirectUrl = savedRequest.redirectUrl
                    response.sendRedirect(redirectUrl)
                 }
            }
            .requestCache {cache -> cache.requestCache(requestCache)}

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