package com.example.userapi.common.security

import com.example.userapi.application.port.out.UserRepositoryPort
import com.example.userapi.domain.exception.UserException
import com.example.userapi.domain.exception.UserExceptionConst
import com.example.userapi.domain.model.CustomUserDetails
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    private val userRepository: UserRepositoryPort
) : UserDetailsService {

    override fun loadUserByUsername(email: String): CustomUserDetails {
        val userEntity = userRepository.findByEmail(email)
            ?: throw UserException(UserExceptionConst.NOT_EXISTS_USER)

        val userDto = userEntity.toDomain()

        return CustomUserDetails(
            id = userDto.id, // todo 보안을 위해 id 노출 제외.
            email = userDto.email,
            password = userDto.password, // todo 보안을 위해 비밀번호 노출 제외.
            authorities = listOf(SimpleGrantedAuthority("ROLE_${userDto.role.name}"))
        )
    }
}
