package com.example.userapi.common

import com.example.userapi.adapter.out.persistence.entity.User
import com.example.userapi.adapter.out.persistence.repository.UserJpaRepository
import com.example.userapi.application.port.out.UserRepositoryPort
import com.example.userapi.domain.model.CustomUserDetails
import com.example.userapi.domain.model.UserAdapterDto
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Aspect
@Component
class UserActivityAspect(
    private val userJpaRepository: UserJpaRepository
) {

    @Autowired
    lateinit var userRepository: UserRepositoryPort

    private val logger: Logger = LoggerFactory.getLogger(UserActivityAspect::class.java)

    // 로그인 성공 후 lastActiveAt 갱신
    //@AfterReturning("execution(* com.example.userapi.adapter.in.web.controller.UserController.login(..))")
    @Transactional
    fun updateLastActiveAtAfterLogin() {
        val currentUser = getCurrentUser()

        currentUser?.lastActiveAt = LocalDateTime.now()

/*        currentUser?.let {
            it.lastActiveAt = LocalDateTime.now()
            // JPA 세션을 통해 변경된 엔티티를 업데이트
        }
        userJpaRepository.save(currentUser)  // `save` 호출 시 자동으로 update가 이루어짐*/
    }

    /*@After(
"execution(* com.example.userapi.adapter.in.web.controller.UserController.getUser(..)) || " +
        "execution(* com.example.userapi.adapter.in.web.controller.UserController.getUsers(..))"
    )*/
    @Transactional
    fun updateLastActiveAtAfterOtherApiCalls() {
        val currentUser = getCurrentUser()

        currentUser?.let {
            it.lastActiveAt = LocalDateTime.now()
            // JPA 세션을 통해 변경된 엔티티를 업데이트
        }
        userJpaRepository.save(currentUser)  // `save` 호출 시 자동으로 update가 이루어짐
    }

    private fun getCurrentUser(): User? {
        val getContext = SecurityContextHolder.getContext()
        val authentication = SecurityContextHolder.getContext().authentication

        val principal = authentication?.principal

        // 인증되지 않은 사용자가 접근한 경우, 예외 대신 로그에만 기록하고 401 응답을 보냄
        return (if (principal is CustomUserDetails) {
            val email = principal.username  // 이메일을 사용자 식별자로 사용
            //userRepository.findByEmail(email) // 이메일로 사용자를 찾는 메서드 호출
            userJpaRepository.findByEmail(email)
        } else {
            // 인증되지 않은 경우 서버 로그에만 기록
            // 로그에 기록: "인증되지 않은 사용자 접근"
            // 예외를 던지지 않고, 클라이언트에게 401 Unauthorized 응답을 보낼 수 있음
            logUnauthorizedAccess()  // 로그 기록 메서드
            null  // 인증되지 않은 사용자라면 null 반환
        })
    }

    private fun logUnauthorizedAccess() {
        // 인증되지 않은 사용자가 접근했을 때 로그 기록
        logger.warn("인증되지 않은 사용자가 접근했습니다.")
    }
}
