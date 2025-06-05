package com.example.userapi.adapter.`in`.web

import com.example.userapi.adapter.`in`.web.dto.*
import com.example.userapi.domain.model.Role
import com.example.userapi.domain.model.User
import com.example.userapi.application.port.`in`.UserUseCase
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper,
    @MockBean var userUseCase: UserUseCase
) {
    @Test
    fun join() {
        val request = SignUpRequest(
            email = "test@example.com",
            password = "Abcdef123!@#",
            name = "홍길동",
            phone = "010-1234-5678"
        )

        mockMvc.perform(
            post("/api/v1/user/users/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.result.code").value("SUCCESS"))
    }

    @Test
    fun `로그인 성공 - 토큰 반환`() {
        val loginRequest = LoginRequest(email = "test@example.com", password = "password123!")
        val tokenResponse = TokenResponse("access-token", "refresh-token")

        `when`(userUseCase.login(loginRequest)).thenReturn(tokenResponse)

        mockMvc.perform(
            post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.data.accessToken").value("access-token"))
            .andExpect(jsonPath("$.data.refreshToken").value("refresh-token"))
    }

    @Test
    @WithMockUser(username = "test@example.com", roles = ["MEMBER"])
    fun `내 정보 조회 - MEMBER 권한`() {
        val user = User(
            email = "test@example.com",
            password = "encrypted",
            name = "홍길동",
            phone = "010-1234-5678",
            role = Role.MEMBER,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            lastActiveAt = LocalDateTime.now()
        )
        val userResponse = UserResponse(
            email = user.email,
            name = user.name,
            phone = user.phone,
            role = user.role,
            createdAt = user.createdAt,
            updatedAt = user.updatedAt,
            lastActiveAt = user.lastActiveAt
        )

        `when`(userUseCase.getUserInfo(user)).thenReturn(userResponse)

        mockMvc.perform(get("/api/users/me"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.data.email").value(user.email))
            .andExpect(jsonPath("$.data.name").value(user.name))
    }

    @Test
    @WithMockUser(roles = ["ADMIN"])
    fun `모든 회원 목록 조회 - ADMIN 권한`() {
        val list = listOf(
            UserSummaryResponse("test1@example.com", "홍길동", "010-1111-2222", LocalDateTime.now()),
            UserSummaryResponse("test2@example.com", "김철수", "010-3333-4444", LocalDateTime.now())
        )

        `when`(userUseCase.getAllUsers()).thenReturn(list)

        mockMvc.perform(get("/api/users"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.data").isArray)
            .andExpect(jsonPath("$.data[0].email").value("test1@example.com"))
    }

    @Test
    fun `모든 회원 목록 조회 - 권한없음 - 403 Forbidden`() {
        mockMvc.perform(get("/api/users"))
            .andExpect(status().isForbidden)
    }

    @Test
    @WithMockUser(roles = ["MEMBER"])
    fun `전화번호 변경 - 본인`() {
        val request = UpdatePhoneRequest(phone = "010-9999-8888")

        mockMvc.perform(
            patch("/api/users/me/phone")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.result.code").value("SUCCESS"))
    }

    @Test
    @WithMockUser(roles = ["ADMIN"])
    fun `관리자 회원 정보 수정`() {
        val request = AdminUpdateRequest(
            name = "변경된 이름",
            phone = "010-7777-6666",
            role = Role.MEMBER
        )

        mockMvc.perform(
            patch("/api/users/test@example.com")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.result.code").value("SUCCESS"))
    }
}
