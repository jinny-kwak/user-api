package com.example.userapi.common.security

import com.example.userapi.application.port.out.UserRepositoryPort
import com.example.userapi.domain.model.CustomUserDetails
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    private val userRepository: UserRepositoryPort
) : UserDetailsService {

    override fun loadUserByUsername(email: String): CustomUserDetails {
        val user = userRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("사용자를 찾을 수 없습니다: $email")

        return CustomUserDetails(
            id = user.id, // todo 보안을 위해 id 노출 제외.
            email = user.email,
            password = user.password, // todo 보안을 위해 비밀번호 노출 제외.
            authorities = listOf(SimpleGrantedAuthority("ROLE_${user.role.name}"))
        )
    }
}
