package com.lecture.security.section04

import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class IndexController {

    @GetMapping("/")
    fun index(authentication: Authentication): Authentication{
        return authentication
    }
}
