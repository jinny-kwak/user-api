package com.example.userapi.adapter.`in`.web.controller

import com.example.userapi.application.port.`in`.UserUseCase
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/access")
open class UserAccessController(
    private val userUseCase: UserUseCase
) {


    @GetMapping("/member/test1")
    @PreAuthorize("hasRole('MEMBER')")
    fun member1() {
        println("@@@@ member")
    }

    @GetMapping("/admin/test1")
    @PreAuthorize("hasRole('ADMIN')")
    fun admin1() {
        println("@@@@ admin")
    }

    @GetMapping("/member/test2")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    fun member2() {
        println("@@@@ member")
    }

    @GetMapping("/admin/test2")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun admin2() {
        println("@@@@ admin")
    }

    @GetMapping("/member/test3")
    @PreAuthorize("hasAuthority('MEMBER')")
    fun member3() {
        println("@@@@ member")
    }

    @GetMapping("/admin/test3")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    fun admin3() {
        println("@@@@ admin")
    }

    @GetMapping("/member/test4")
    @PreAuthorize("hasAuthority('ROLE_MEMBER')")
    fun member4() {
        println("@@@@ member")
    }

    @GetMapping("/admin/test4")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    fun admin4() {
        println("@@@@ admin")
    }

}
