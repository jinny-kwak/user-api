package com.example.userapi.application.port.out

import com.example.userapi.domain.model.User

interface UserRepositoryPort {
    fun save(user: User): User
    fun findByEmail(email: String): User?
    fun findAll(): List<User>
    fun existsByEmail(email: String): Boolean
}
