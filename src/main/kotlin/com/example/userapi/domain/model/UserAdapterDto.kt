package com.example.userapi.domain.model

import java.time.LocalDateTime

class UserAdapterDto(

    val id: Long? = null,
    val email: String,
    var password: String,
    var name: String,
    var phone: String,
    var role: Role = Role.MEMBER,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now(),
    var lastActiveAt: LocalDateTime = LocalDateTime.now()
) {



}
