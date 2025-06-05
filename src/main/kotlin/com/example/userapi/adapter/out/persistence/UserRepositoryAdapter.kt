package com.example.userapi.adapter.out.persistence

import com.example.userapi.domain.model.User
import com.example.userapi.application.port.out.UserRepositoryPort
import com.example.userapi.adapter.out.persistence.entity.UserEntity
import com.example.userapi.adapter.out.persistence.repository.UserJpaRepository
import org.springframework.stereotype.Component

@Component
class UserRepositoryAdapter(
    private val userJpaRepository: UserJpaRepository
) : UserRepositoryPort {

    override fun save(user: User): User {
        val entity = userJpaRepository.findByEmail(user.email)?.apply {
            updateFromDomain(user)
        } ?: UserEntity(
            email = user.email,
            password = user.password,
            name = user.name,
            phone = user.phone,
            role = user.role,
            createdAt = user.createdAt,
            updatedAt = user.updatedAt,
            lastActiveAt = user.lastActiveAt
        )

        val saved = userJpaRepository.save(entity)
        return saved.toDomain()
    }


    override fun findByEmail(email: String): User? {
        return userJpaRepository.findByEmail(email)?.toDomain()
    }

    override fun findAll(): List<User> {
        return userJpaRepository.findAll().map { it.toDomain() }
    }

    override fun existsByEmail(email: String): Boolean {
        return userJpaRepository.existsByEmail(email)
    }
}
