package com.lecture.security.section06

import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {

    @GetMapping("/")
    fun index(authentication: Authentication): Authentication {
        return authentication
    }

    @GetMapping("/login")
    fun login(): String {
        return "login"
    }

    @GetMapping("/denied")
    fun denied(): String {
        return "denied"
    }
}