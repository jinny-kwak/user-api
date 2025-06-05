package com.example.userapi.adapter.out.persistence.entity

import com.example.userapi.domain.model.Role
import com.example.userapi.domain.model.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    var password: String,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var phone: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var role: Role = Role.MEMBER,

    @Column(nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    var updatedAt: LocalDateTime = LocalDateTime.now(),

    var lastActiveAt: LocalDateTime = LocalDateTime.now()
) {

    fun toDomain(): User {
        return User(
            email = email,
            password = password,
            name = name,
            phone = phone,
            role = role,
            createdAt = createdAt,
            updatedAt = updatedAt,
            lastActiveAt = lastActiveAt
        )
    }

    fun updateFromDomain(user: User) {
        this.password = user.password
        this.name = user.name
        this.phone = user.phone
        this.role = user.role
        this.updatedAt = LocalDateTime.now()
        this.lastActiveAt = user.lastActiveAt
    }
}