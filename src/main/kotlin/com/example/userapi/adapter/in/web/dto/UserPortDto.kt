package com.example.userapi.adapter.`in`.web.dto

import com.example.userapi.domain.model.Role
import java.time.LocalDateTime

class UserPortDto {
    class In {
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

        data class GetUsersRequest(
            val startDate: LocalDateTime? = null,
            val endDate: LocalDateTime? = null
        )

        data class UpdateUserInfoRequest(
            //val password: String,
            val name: String,
            val phone: String,
            val role: Role
        )

        data class UpdateMemberRequest(
            val phone: String
        )

        data class UpdateAdminRequest(
            val password: String,
            val name: String,
            val phone: String,
            val role: Role
        )

        companion object {
            fun from(): SignUpRequest {
                return SignUpRequest(
                    email = "",
                    password = "",
                    name = "",
                    phone = ""
                )
            }
        }

    }

    class Out {
        data class SignUpResponse(
            val email: String,
            val name: String,
            val phone: String
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

        data class UsersResponse(
            val email: String,
            val name: String,
            val phone: String,
            val createdAt: LocalDateTime
        )

        data class UpdateMemberResponse(
            val email: String?,
            val phone: String?
        )

        data class AdminUpdateResponse(
            val email: String?,
            val password: String?,
            val name: String?,
            val phone: String?,
            val role: Role
        )

    }




}