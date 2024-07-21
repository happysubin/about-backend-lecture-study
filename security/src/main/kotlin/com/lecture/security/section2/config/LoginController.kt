package com.lecture.security.section2.config

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class LoginController {

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
}