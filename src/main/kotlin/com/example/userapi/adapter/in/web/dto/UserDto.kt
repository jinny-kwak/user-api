package com.example.userapi.adapter.`in`.web.dto

import com.example.userapi.domain.model.Role
import java.time.LocalDateTime

data class SignUpRequest(
    val email: String,
    val password: String,
    val name: String,
    val phone: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)

data class UserResponse(
    val email: String,
    val name: String,
    val phone: String,
    val role: Role,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val lastActiveAt: LocalDateTime
)

data class UserSummaryResponse(
    val email: String,
    val name: String,
    val phone: String,
    val createdAt: LocalDateTime
)

data class UpdatePhoneRequest(
    val phone: String
)

data class AdminUpdateRequest(
    val name: String,
    val phone: String,
    val role: Role
)
