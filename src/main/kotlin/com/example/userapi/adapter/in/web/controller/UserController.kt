package com.example.userapi.adapter.`in`.web.controller

import com.example.userapi.adapter.`in`.web.dto.*
import com.example.userapi.application.service.CurrentUser
import com.example.userapi.domain.model.User
import com.example.userapi.application.port.`in`.UserUseCase
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/user/users")
open class UserController(
    private val userUseCase: UserUseCase
) {

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    fun signUp(@RequestBody request: SignUpRequest): ApiResponse<Unit> {
        userUseCase.signUp(request)
        return ResponseFactory.success(Unit)
    }

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ApiResponse<TokenResponse> {
        val token = userUseCase.login(request)
        return ResponseFactory.success(token)
    }

    @GetMapping("/me")
    fun getMyInfo(@CurrentUser user: User): ApiResponse<UserResponse> {
        val data = userUseCase.getUserInfo(user)
        return ResponseFactory.success(data)
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    fun getAllUsers(): ApiResponse<List<UserSummaryResponse>> {
        val users = userUseCase.getAllUsers()
        return ResponseFactory.success(users)
    }

    @PatchMapping("/me/phone")
    fun updatePhone(
        @CurrentUser user: User,
        @RequestBody request: UpdatePhoneRequest
    ): ApiResponse<Unit> {
        userUseCase.updatePhone(user, request)
        return ResponseFactory.success(Unit)
    }

    @PatchMapping("/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    fun adminUpdate(
        @PathVariable email: String,
        @RequestBody request: AdminUpdateRequest
    ): ApiResponse<Unit> {
        userUseCase.adminUpdateUser(email, request)
        return ResponseFactory.success(Unit)
    }
}
