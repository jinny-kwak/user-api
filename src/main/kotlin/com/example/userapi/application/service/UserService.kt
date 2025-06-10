package com.example.userapi.application.service


import com.example.userapi.adapter.`in`.web.dto.PageResponse
import com.example.userapi.adapter.`in`.web.dto.UserPortDto
import com.example.userapi.adapter.out.persistence.entity.User
import com.example.userapi.domain.model.UserAdapterDto
import com.example.userapi.infrastructure.security.JwtProvider
import com.example.userapi.application.port.`in`.UserUseCase
import com.example.userapi.application.port.out.UserRepositoryPort
import com.example.userapi.domain.exception.UserExceptionConst
import com.example.userapi.domain.exception.UserNotFoundException
import com.example.userapi.util.isValidEmail
import com.example.userapi.util.isValidPassword
import org.springframework.data.domain.Pageable
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class UserService(
    private val userRepository: UserRepositoryPort,
    private val passwordEncoder: PasswordEncoder,
    private val jwtProvider: JwtProvider
) : UserUseCase {

    @Transactional
    override fun signUp(request: UserPortDto.In.SignUpRequest): UserPortDto.Out.SignUpResponse {
        validationSignUp(request)

        val userAdapterDto = UserAdapterDto(
            email = request.email,
            password = passwordEncoder.encode(request.password),
            name = request.name,
            phone = request.phone
        )

        val savedUserDto = userRepository.save(userAdapterDto)

        return UserPortDto.Out.SignUpResponse(
            email = savedUserDto.email,
            name = savedUserDto.name,
            phone = savedUserDto.phone
        )
    }

    private fun validationSignUp(request: UserPortDto.In.SignUpRequest) {
        if (!isValidEmail(request.email))
            throw IllegalArgumentException("이메일 정규식을 확인해주세요.")
        //throw MemberException(MemberErrorCode.NOT_FORMATTED_EMAIL)

        if (!isValidPassword(request.password))
            throw IllegalArgumentException("영어 대문자, 영어 소문자, 숫자, 특수문자 중 3종류 이상으로 12자리 이상의 문자열 이어야 합니다.")

        if (userRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException("이미 존재하는 이메일입니다.")
        }
    }

    @Transactional(readOnly = true)
    override fun login(request: UserPortDto.In.LoginRequest): UserPortDto.Out.TokenResponse {

        val userDto = userRepository.findByEmail(request.email)
            ?: throw UserNotFoundException(UserExceptionConst.NOT_EXISTS_USER)

        if (!passwordEncoder.matches(request.password, userDto.password)) {
            throw IllegalArgumentException("비밀번호가 일치하지 않습니다.")
        }

    return UserPortDto.Out.TokenResponse(
        accessToken = jwtProvider.createAccessToken(userDto),
        refreshToken = jwtProvider.createRefreshToken(userDto)
    )
}

    @Transactional(readOnly = true)
    override fun getUserBy(userId: Long): UserPortDto.Out.UserResponse {
        val userDto = userRepository.findById(userId)
            ?: throw UserNotFoundException(UserExceptionConst.NOT_EXISTS_USER)

        return UserPortDto.Out.UserResponse(
            email = userDto.email,
            name = userDto.name,
            phone = userDto.phone,
            role = userDto.role,
            createdAt = userDto.createdAt,
            updatedAt = userDto.updatedAt,
            lastActiveAt = userDto.lastActiveAt
        )
    }


    @Transactional(readOnly = true)
    override fun getUsers(
        pageable: Pageable,
        getUsersRequest: UserPortDto.In.GetUsersRequest
    ): List<UserPortDto.Out.UsersResponse> {
        val users = userRepository.findAll(pageable)

        return users.map { userAdapterDto ->
            UserPortDto.Out.UsersResponse(
                name = userAdapterDto.name,
                email = userAdapterDto.email,
                phone = userAdapterDto.phone,
                createdAt = userAdapterDto.createdAt
            )
        }
    }

    @Transactional(readOnly = true)
    override fun getUsers2(
        pageable: Pageable,
        getUsersRequest: UserPortDto.In.GetUsersRequest
    ): PageResponse<UserPortDto.Out.UsersResponse> {
        val users = userRepository.findAll2(pageable)
        return PageResponse<UserPortDto.Out.UsersResponse>().of(users)
    }

    @Transactional
    override fun updateUserInfoByMember(
        userId: Long,
        request: UserPortDto.In.UpdateUserInfoRequest
    ): UserPortDto.Out.AdminUpdateResponse {
        val userEntity = userRepository.findByIdOrNull(userId)
            ?: throw UserNotFoundException(UserExceptionConst.NOT_EXISTS_USER)

        // todo 비밀번호 수정할 경우 인코딩 passwordEncoder.encode(request.password)
        userEntity.updateByMember(request.phone)

        return UserPortDto.Out.AdminUpdateResponse(
            email = userEntity.email,
            password = userEntity.password,
            name = userEntity.name,
            phone = userEntity.phone,
            role = userEntity.role,
        )
    }

    @Transactional
    override fun updateUserInfoByAdmin(
        userId: Long,
        request: UserPortDto.In.UpdateUserInfoRequest
    ): UserPortDto.Out.AdminUpdateResponse {
        val userEntity = userRepository.findByIdOrNull(userId)
            ?: throw UserNotFoundException(UserExceptionConst.NOT_EXISTS_USER)

        /**
         * todo 전화번호 수정만 가능하도록 제한
         * 비밀번호 경우 - val isPasswordMatch = passwordEncoder.matches(inputPassword, storedEncodedPassword)
         */

        userEntity.updateByAdmin(request)

        return UserPortDto.Out.AdminUpdateResponse(
            email = userEntity.email,
            password = userEntity.password,
            name = userEntity.name,
            phone = userEntity.phone,
            role = userEntity.role,
        )
    }

    /**
     * todo MEMBER validation
     * 전화번호 외 항목을 수정하려고 하면 권한 없음 처리
     * 전화번호 제외한 나머지 컬럼 깂이랑 db 값이랑 다르면 Exception 처리
     * userEntity != request 값 throw
     * 만약에 null 값이 들어오면 null 체크 후 Exception 처리
     */
    private fun isValidUpdateUserInfoByMember(
        request: UserPortDto.In.UpdateUserInfoRequest,
        userEntity: User
    ): Boolean {
        //if (userEntity.name != request.name || userEntity.role != request.role || userEntity.password != request.password) {

        //}
        return true
    }


}