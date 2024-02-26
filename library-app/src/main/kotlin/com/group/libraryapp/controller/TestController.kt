package com.group.libraryapp.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

@RestController
class TestController {

    private val counter = AtomicInteger(0)

    @GetMapping("/cnt")
    fun counter(): Dto {
        return Dto(UUID.randomUUID().toString(), counter.getAndAdd(1));
    }
}

data class Dto(
    val message: String,
    val count: Int
)