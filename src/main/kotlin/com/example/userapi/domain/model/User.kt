package com.example.userapi.domain.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "users")
class User(
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

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    var lastActiveAt: LocalDateTime = LocalDateTime.now()
) {
    fun updatePhone(newPhone: String) {
        phone = newPhone
        updatedAt = LocalDateTime.now()
    }

    fun updateByAdmin(name: String, phone: String, role: Role) {
        this.name = name
        this.phone = phone
        this.role = role
        this.updatedAt = LocalDateTime.now()
    }

    fun markActive() {
        this.lastActiveAt = LocalDateTime.now()
    }
}
