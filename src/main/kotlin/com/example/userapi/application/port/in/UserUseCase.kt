package com.example.userapi.application.port.`in`

import com.example.userapi.adapter.`in`.web.dto.PageResponse
import com.example.userapi.adapter.`in`.web.dto.UserAdapterDtoV2
import com.example.userapi.adapter.`in`.web.dto.UserPortDto
import com.example.userapi.domain.dto.UserPortDtoV2
import org.springframework.data.domain.Pageable

interface UserUseCase {
//    fun signUp(request: UserPortDto.In.SignUpRequest): UserPortDto.Out.SignUpResponse
    fun signUp(request: UserPortDtoV2.In.SignUpRequest): UserPortDto.Out.SignUpResponse
    fun login(request: UserPortDto.In.LoginRequest): UserPortDto.Out.TokenResponse
    fun getUserById(userId: Long): UserPortDto.Out.UserResponse
//    fun getUsers(pageable: Pageable, getUsersRequest: UserPortDto.In.GetUsersRequest): List<UserPortDto.Out.UsersResponse>
    fun getUsers(pageable: Pageable, getUsersRequest: UserPortDto.In.GetUsersRequest): PageResponse<UserPortDto.Out.UsersResponse>
    fun updateUserInfoByMember(userId: Long, request: UserPortDto.In.UpdateUserInfoRequest): UserPortDto.Out.AdminUpdateResponse
    fun updateUserInfoByAdmin(userId: Long, request: UserPortDto.In.UpdateUserInfoRequest): UserPortDto.Out.AdminUpdateResponse
}
