package com.example.userapi.application.port.out

import com.example.userapi.adapter.out.persistence.entity.User
import com.example.userapi.domain.model.UserAdapterDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface UserRepositoryPort {
    fun save(userAdapterDto: UserAdapterDto?): User
    fun findByEmail(email: String): User?
    fun findByIdOrNull(id: Long): User?
    fun findAll(pageable: Pageable): List<User>
    fun findAll2(pageable: Pageable): Page<User>
    fun existsByEmail(email: String): Boolean
}
