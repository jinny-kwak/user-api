package com.example.userapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
open class UserApiApplication

fun main(args: Array<String>) {
    runApplication<UserApiApplication>(*args)
}
