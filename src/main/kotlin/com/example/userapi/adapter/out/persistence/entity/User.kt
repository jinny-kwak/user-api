package com.example.userapi.adapter.out.persistence.entity

import com.example.userapi.adapter.`in`.web.dto.UserPortDto
import com.example.userapi.domain.model.BaseTimeEntity
import com.example.userapi.domain.model.Role
import com.example.userapi.domain.model.UserAdapterDto
import jakarta.persistence.*
import org.hibernate.annotations.Comment
import java.time.LocalDateTime

@Entity
@Table(name = "user")
class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, columnDefinition = "bigint")
    @Comment("테이블 PK")
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    val email: String? = null,

    @Column(nullable = false)
    var password: String? = null,

    @Column(nullable = false)
    var name: String? = null,

    @Column(nullable = false)
    var phone: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var role: Role = Role.MEMBER,

    @Column(nullable = false, updatable = false)
    var lastActiveAt: LocalDateTime? = LocalDateTime.now()
) : BaseTimeEntity() {

    constructor() : this(
        email = null,
        password = null,
        name = null,
        phone = null,
        role = Role.MEMBER,
        lastActiveAt = null
    )

    fun toDomain(): UserAdapterDto {
        return UserAdapterDto(
            id = id,
            email = email ?: "",
            password = password ?: "",
            name = name ?: "",
            phone = phone ?: "",
            role = role ?: Role.MEMBER,
            createdAt = createdAt ?: LocalDateTime.now(),
            updatedAt = updatedAt ?: LocalDateTime.now(),
            lastActiveAt = lastActiveAt ?: LocalDateTime.now()
        )
    }

    fun updateFromDomain(userAdapterDto: UserAdapterDto) {
        this.password = userAdapterDto.password
        this.name = userAdapterDto.name
        this.phone = userAdapterDto.phone
        this.updatedAt = LocalDateTime.now()
        this.lastActiveAt = userAdapterDto.lastActiveAt
    }

    fun updateByMember(newPhone: String) {
        this.phone = newPhone
        this.updatedAt = LocalDateTime.now()
        this.lastActiveAt = LocalDateTime.now()
    }

    fun updateByAdmin(request: UserPortDto.In.UpdateUserInfoRequest) {
        this.name = request.name
        this.phone = request.phone
        //this.password = request.,password
        this.role = request.role
        this.updatedAt = LocalDateTime.now()
    }

    fun markActive() {
        this.lastActiveAt = LocalDateTime.now()
    }
}