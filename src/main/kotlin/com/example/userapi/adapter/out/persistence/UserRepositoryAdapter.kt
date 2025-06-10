package com.example.userapi.adapter.out.persistence

import com.example.userapi.domain.model.UserAdapterDto
import com.example.userapi.application.port.out.UserRepositoryPort
import com.example.userapi.adapter.out.persistence.entity.User
import com.example.userapi.adapter.out.persistence.repository.UserJpaRepository
import com.example.userapi.domain.model.Role
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import kotlin.jvm.optionals.getOrNull

@Component
class UserRepositoryAdapter(
    private val userJpaRepository: UserJpaRepository
) : UserRepositoryPort {

    override fun save(userAdapterDto: UserAdapterDto?): UserAdapterDto {
        // New 유저 객체 생성
        val user = User(
            email = userAdapterDto?.email,
            password = userAdapterDto?.password,
            name = userAdapterDto?.name,
            phone = userAdapterDto?.phone,
            role = userAdapterDto?.role ?: Role.MEMBER,
            lastActiveAt = userAdapterDto?.lastActiveAt
        )

        val savedUser = userJpaRepository.save(user)
        return savedUser.toDomain()
    }

    override fun findByEmail(email: String): UserAdapterDto? {
        return userJpaRepository.findByEmail(email)?.toDomain()
    }

    override fun findByIdOrNull(userId: Long): User? {
        return userJpaRepository.findByIdOrNull(userId)
    }

    override fun findById(userId: Long): UserAdapterDto? {
        return userJpaRepository.findById(userId).orElse(null).toDomain()
    }

    override fun findAll(pageable: Pageable): List<UserAdapterDto> {
        return userJpaRepository.findAll(pageable).content.map { it.toDomain() }
    }

    override fun findAll2(pageable: Pageable): Page<User> {
        return userJpaRepository.findAll(pageable)
    }

    override fun existsByEmail(email: String): Boolean {
        return userJpaRepository.existsByEmail(email)
    }
}
