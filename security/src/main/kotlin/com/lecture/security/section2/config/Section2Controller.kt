package com.lecture.security.section2.config

import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.CurrentSecurityContext
import org.springframework.security.core.context.SecurityContext
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController("/login")
class Section2Controller {

    @GetMapping
    fun index(): String {
        return "index"
    }

    @GetMapping("/home")
    fun home(): String {
        return "home"
    }

    @GetMapping("/loginPage")
    fun loginProc(): String {
        return "loginPage"
    }

    @GetMapping("/anonymous")
    fun anonymous(): String {
        return "anonymous"
    }



    @GetMapping("/authentication")
    fun authentication(authentication: Authentication?): String { //익명일 때 이 값은 항상 null. 따라서 아래 코드를 사용해야함
        return if (authentication is AnonymousAuthenticationToken) {
            "anonymous";
        } else {
            "not anonymous";
        }
    }

    @GetMapping("/anonymousContext")
    fun anonymousContext(@CurrentSecurityContext context: SecurityContext): String {
        return context.authentication.name
    }
}