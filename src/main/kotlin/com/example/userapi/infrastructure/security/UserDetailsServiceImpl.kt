    package com.example.userapi.infrastructure.security

    import com.example.userapi.application.port.out.UserRepositoryPort
    import org.springframework.security.core.GrantedAuthority
    import org.springframework.security.core.authority.SimpleGrantedAuthority
    import org.springframework.security.core.userdetails.UserDetails
    import org.springframework.security.core.userdetails.UserDetailsService
    import org.springframework.security.core.userdetails.UsernameNotFoundException
    import org.springframework.stereotype.Service

    @Service
    class UserDetailsServiceImpl(
        private val userRepository: UserRepositoryPort
    ) : UserDetailsService {

        override fun loadUserByUsername(email: String): UserDetails {
            val user = userRepository.findByEmail(email)
                ?: throw UsernameNotFoundException("사용자를 찾을 수 없습니다: $email")

            return object : UserDetails {
                override fun getAuthorities(): Collection<GrantedAuthority> =
                    listOf(SimpleGrantedAuthority("ROLE_${user.role.name}"))

                override fun getPassword(): String = user.password
                override fun getUsername(): String = user.email
                override fun isAccountNonExpired(): Boolean = true
                override fun isAccountNonLocked(): Boolean = true
                override fun isCredentialsNonExpired(): Boolean = true
                override fun isEnabled(): Boolean = true
            }
        }
    }
