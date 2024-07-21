package com.lecture.security

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["com.lecture.security.section2.config"])
class SecurityApplication

fun main(args: Array<String>) {
	runApplication<SecurityApplication>(*args)
}
