package com.example.userapi.application.port.`in`

import com.example.userapi.adapter.`in`.web.dto.*
import com.example.userapi.domain.model.User

interface UserUseCase {
    fun signUp(request: SignUpRequest)
    fun login(request: LoginRequest): TokenResponse
    fun getUserInfo(currentUser: User): UserResponse
    fun getAllUsers(): List<UserSummaryResponse>
    fun updatePhone(currentUser: User, request: UpdatePhoneRequest)
    fun adminUpdateUser(email: String, request: AdminUpdateRequest)
}
