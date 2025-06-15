package com.example.userapi.adapter.`in`.web.controller

import com.example.userapi.adapter.`in`.web.dto.ApiResponse
import com.example.userapi.adapter.`in`.web.dto.PageResponse
import com.example.userapi.adapter.`in`.web.dto.ResponseFactory
import com.example.userapi.adapter.`in`.web.dto.UserPortDto
import com.example.userapi.application.port.`in`.UserUseCase
import com.example.userapi.common.exception.CommonException
import com.example.userapi.common.exception.CommonExceptionConst
import com.example.userapi.domain.model.CustomUserDetails
import com.example.userapi.domain.model.Role
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userUseCase: UserUseCase
) {

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    fun signUp(
        @Valid @RequestBody request: UserPortDto.In.SignUpRequest
    ): ResponseEntity<ApiResponse<UserPortDto.Out.SignUpResponse>> {
        val signUpResponse = userUseCase.signUp(request)
        return ResponseFactory.success(signUpResponse)
    }

    @PostMapping("/login")
    fun login(
        @RequestBody request: UserPortDto.In.LoginRequest
    ): ResponseEntity<ApiResponse<UserPortDto.Out.TokenResponse>> {
        val tokenResponse = userUseCase.login(request)
        return ResponseFactory.success(tokenResponse)
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('MEMBER') and #userId.toString() == authentication.principal.id.toString())")
    @ResponseBody
    fun getUser(
        @PathVariable(value = "userId", required = true) userId: Long
    ): ResponseEntity<ApiResponse<UserPortDto.Out.UserResponse>> {
        val userResponse = userUseCase.getUserBy(userId)
        return ResponseFactory.success(userResponse)
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    fun getUsers(
        pageable: Pageable,
        getUsersRequest: UserPortDto.In.GetUsersRequest
    ): ResponseEntity<ApiResponse<List<UserPortDto.Out.UsersResponse>>> {
        val users = userUseCase.getUsers(pageable, getUsersRequest)
        return ResponseFactory.success(users)
    }

    @GetMapping("/v2")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    fun getUsers2(
        pageable: Pageable,
        getUsersRequest: UserPortDto.In.GetUsersRequest
    ): ResponseEntity<ApiResponse<PageResponse<UserPortDto.Out.UsersResponse>>> {
        val users = userUseCase.getUsers2(pageable, getUsersRequest)
        return ResponseFactory.success(users)
    }

    @PatchMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('MEMBER') and #userId.toString() == authentication.principal.id.toString())")
    fun updateUser(
        @PathVariable userId: Long,
        @RequestBody request: UserPortDto.In.UpdateUserInfoRequest,
        @AuthenticationPrincipal currentUser: CustomUserDetails  // @AuthenticationPrincipal을 사용하여 로그인된 사용자 정보 주입
    ): ResponseEntity<ApiResponse<UserPortDto.Out.AdminUpdateResponse>> {

        var updateUserInfo: UserPortDto.Out.AdminUpdateResponse? = null

        /**
         * MEMBER 권한 경우
         * - 프론트에서 전체 정보 조회 후, 전화번호 필드만 수정할 수 있도록 가정
         * - 수정 요청 시, 기존 + 수정 데이터 받음.
         * - JPA 영속성 컨텍스트에서 수정된 데이터만 스냅샵 비교로 update 처리
         */
        // MEMBER인 경우, 본인의 전화번호만 수정할 수 있도록 제한
        if (currentUser.id == userId) {
            updateUserInfo = userUseCase.updateUserInfoByAdmin(userId, request)
            return ResponseFactory.success(updateUserInfo)
        }

        // ADMIN인 경우, 모든 정보를 수정할 수 있도록 허용
        if (currentUser.authorities.any {it.authority == "ROLE_${Role.ADMIN}"}) {
            updateUserInfo = userUseCase.updateUserInfoByAdmin(userId, request)
        }

        if (updateUserInfo != null) {
            return ResponseFactory.success(updateUserInfo)
        } else {
            // 수정 권한이 없는 사용자인 경우
            // todo 수정 권한이 없으면 애초에 security에서 걸러지도록 설정되어 있음. updateUserInfo가 null인 경우가 발생 하는가?
            throw CommonException(CommonExceptionConst.NO_UPDATE_PERMISSION)
        }
    }

}
