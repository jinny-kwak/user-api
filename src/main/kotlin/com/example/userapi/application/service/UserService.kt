package com.example.userapi.application.service


import com.example.userapi.adapter.`in`.web.dto.PageResponse
import com.example.userapi.adapter.`in`.web.dto.UserAdapterDtoV2
import com.example.userapi.adapter.`in`.web.dto.UserPortDto
import com.example.userapi.adapter.out.persistence.entity.User
import com.example.userapi.application.port.`in`.UserUseCase
import com.example.userapi.application.port.out.UserRepositoryPort
import com.example.userapi.common.security.JwtProvider
import com.example.userapi.domain.dto.UserPortDtoV2
import com.example.userapi.domain.exception.UserException
import com.example.userapi.domain.exception.UserExceptionConst
import com.example.userapi.domain.model.UserAdapterDto
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
//    override fun signUp(request: UserPortDto.In.SignUpRequest): UserPortDto.Out.SignUpResponse {
    override fun signUp(request: UserPortDtoV2.In.SignUpRequest): UserPortDto.Out.SignUpResponse {
        validationSignUp(request)

        // todo Port로 변경
        val userAdapterDto = UserAdapterDto(
            email = request.email,
            password = passwordEncoder.encode(request.password),
            name = request.name,
            phone = request.phone
        )

        val userEntity = userRepository.save(userAdapterDto) // todo save() 파라미터 port로 변경
        val savedUserDto = userEntity.toDomain()

        return UserPortDto.Out.SignUpResponse(
            email = savedUserDto.email,
            name = savedUserDto.name,
            phone = savedUserDto.phone
        )
    }

//    private fun validationSignUp(request: UserPortDto.In.SignUpRequest) {
    private fun validationSignUp(request: UserPortDtoV2.In.SignUpRequest) {
        if (!isValidEmail(request.email))
            throw UserException(UserExceptionConst.NOT_FORMATTED_EMAIL)

        if (!isValidPassword(request.password))
            throw UserException(UserExceptionConst.NOT_FORMATTED_PASSWORD)

        if (userRepository.existsByEmail(request.email)) {
            throw UserException( UserExceptionConst.ALREADY_EXISTS_EMAIL)
        }
    }

    @Transactional(readOnly = true)
    override fun login(request: UserPortDto.In.LoginRequest): UserPortDto.Out.TokenResponse {

        val userEntity = userRepository.findByEmail(request.email)
            ?: throw UserException(UserExceptionConst.NOT_EXISTS_USER)
        val userDto = userEntity.toDomain()

        if (!passwordEncoder.matches(request.password, userDto.password)) {
            throw UserException(UserExceptionConst.INVALID_PASSWORD)
        }

    return UserPortDto.Out.TokenResponse(
        accessToken = jwtProvider.createAccessToken(userDto),
        refreshToken = jwtProvider.createRefreshToken(userDto)
    )
}

    @Transactional(readOnly = true)
    override fun getUserById(userId: Long): UserPortDto.Out.UserResponse {
        val userEntity = userRepository.findByIdOrNull(userId)
            ?: throw UserException(UserExceptionConst.NOT_EXISTS_USER)
        val userDto = userEntity.toDomain()

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


/*    @Transactional(readOnly = true)
    override fun getUsers(
        pageable: Pageable,
        getUsersRequest: UserPortDto.In.GetUsersRequest
    ): List<UserPortDto.Out.UsersResponse> {
        val userEntities = userRepository.findAll(pageable)

        return userEntities.map { user ->
            val userDto = user.toDomain()
            UserPortDto.Out.UsersResponse(
                name = userDto.name,
                email = userDto.email,
                phone = userDto.phone,
                createdAt = userDto.createdAt
            )
        }
    }*/

    @Transactional(readOnly = true)
    override fun getUsers(
        pageable: Pageable,
        getUsersRequest: UserPortDto.In.GetUsersRequest
    ): PageResponse<UserPortDto.Out.UsersResponse> {
        val users = userRepository.findAll(pageable)
        return PageResponse<UserPortDto.Out.UsersResponse>().of(users)
    }

    @Transactional
    override fun updateUserInfoByMember(
        userId: Long,
        request: UserPortDto.In.UpdateUserInfoRequest
    ): UserPortDto.Out.AdminUpdateResponse {
        val userEntity = userRepository.findByIdOrNull(userId)
            ?: throw UserException(UserExceptionConst.NOT_EXISTS_USER)

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
            ?: throw UserException(UserExceptionConst.NOT_EXISTS_USER)

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