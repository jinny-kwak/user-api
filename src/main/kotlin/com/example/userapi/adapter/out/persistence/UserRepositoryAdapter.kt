package com.example.userapi.adapter.out.persistence

import com.example.userapi.adapter.out.persistence.entity.User
import com.example.userapi.adapter.out.persistence.repository.UserJpaRepository
import com.example.userapi.application.port.out.UserRepositoryPort
import com.example.userapi.domain.model.Role
import com.example.userapi.domain.model.UserAdapterDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class UserRepositoryAdapter(
    private val userJpaRepository: UserJpaRepository
) : UserRepositoryPort {

    override fun save(userAdapterDto: UserAdapterDto?): User {
        // New 유저 객체 생성
        val user = User(
            email = userAdapterDto?.email,
            password = userAdapterDto?.password,
            name = userAdapterDto?.name,
            phone = userAdapterDto?.phone,
            role = userAdapterDto?.role ?: Role.MEMBER,
            lastActiveAt = userAdapterDto?.lastActiveAt
        )

        return userJpaRepository.save(user)
    }

    override fun findByEmail(email: String): User? {
        return userJpaRepository.findByEmail(email)
    }

    override fun findByIdOrNull(userId: Long): User? {
        return userJpaRepository.findByIdOrNull(userId)
    }

/*    override fun findAll(pageable: Pageable): List<User> {
        return userJpaRepository.findAll(pageable).content
    }*/

    override fun findAll(pageable: Pageable): Page<User> {
        return userJpaRepository.findAll(pageable)
    }

    override fun existsByEmail(email: String): Boolean {
        return userJpaRepository.existsByEmail(email)
    }
}
