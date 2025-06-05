package com.example.userapi.application.service

import com.example.userapi.adapter.`in`.web.dto.AdminUpdateRequest
import com.example.userapi.adapter.`in`.web.dto.LoginRequest
import com.example.userapi.adapter.`in`.web.dto.SignUpRequest
import com.example.userapi.adapter.`in`.web.dto.TokenResponse
import com.example.userapi.adapter.`in`.web.dto.UpdatePhoneRequest
import com.example.userapi.adapter.`in`.web.dto.UserResponse
import com.example.userapi.adapter.`in`.web.dto.UserSummaryResponse
import com.example.userapi.domain.model.User
import com.example.userapi.infrastructure.security.JwtProvider
import com.example.userapi.application.port.`in`.UserUseCase
import com.example.userapi.application.port.out.UserRepositoryPort
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
open class UserService(
    private val userRepository: UserRepositoryPort,
    private val passwordEncoder: PasswordEncoder,
    private val jwtProvider: JwtProvider
) : UserUseCase {

    @Transactional
    override fun signUp(request: SignUpRequest) {

        if (userRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException("이미 존재하는 이메일입니다.")
        }

        val user = User(
            email = request.email,
            password = passwordEncoder.encode(request.password),
            name = request.name,
            phone = request.phone
        )

        userRepository.save(user)
    }

    @Transactional(readOnly = true)
    override fun login(request: LoginRequest): TokenResponse {
        val user = userRepository.findByEmail(request.email)
            ?: throw IllegalArgumentException("가입된 사용자가 아닙니다.")

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw IllegalArgumentException("비밀번호가 일치하지 않습니다.")
        }

        return TokenResponse(
            accessToken = jwtProvider.createAccessToken(user),
            refreshToken = jwtProvider.createRefreshToken(user)
        )
    }

    @Transactional(readOnly = true)
    override fun getUserInfo(currentUser: User): UserResponse {
        return UserResponse(
            email = currentUser.email,
            name = currentUser.name,
            phone = currentUser.phone,
            role = currentUser.role,
            createdAt = currentUser.createdAt,
            updatedAt = currentUser.updatedAt,
            lastActiveAt = currentUser.lastActiveAt
        )
    }

    @Transactional(readOnly = true)
    override fun getAllUsers(): List<UserSummaryResponse> {
        return userRepository.findAll().map {
            UserSummaryResponse(it.email, it.name, it.phone, it.createdAt)
        }
    }

    @Transactional
    override fun updatePhone(currentUser: User, request: UpdatePhoneRequest) {
        currentUser.updatePhone(request.phone)
        userRepository.save(currentUser)
    }

    @Transactional
    override fun adminUpdateUser(email: String, request: AdminUpdateRequest) {
        val user = userRepository.findByEmail(email)
            ?: throw IllegalArgumentException("해당 사용자를 찾을 수 없습니다.")
        user.updateByAdmin(request.name, request.phone, request.role)
        userRepository.save(user)
    }
}