package com.example.userapi.common.exception

import com.example.userapi.adapter.`in`.web.dto.ApiResponse
import com.example.userapi.adapter.`in`.web.dto.ResponseFactory
import com.example.userapi.domain.exception.EmailAlreadyExistException
import com.example.userapi.domain.exception.UserNotFoundException
import io.jsonwebtoken.MalformedJwtException
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus.*
import org.springframework.security.authorization.AuthorizationDeniedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    private val logger: Logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(UserNotFoundException::class)
    fun handleNotFoundException(request: HttpServletRequest, ex: UserNotFoundException): ApiResponse<Nothing> {
        logger.error("존재하지 않는 사용자 발생: ${ex.javaClass.name} ${request.requestURI} ${ex.message}")
//        logger.error("존재하지 않는 사용자 발생: {} {}", ex.javaClass.name, request.requestURI, ex)
        return ResponseFactory.fail(ex.getErrorCode(), ex.getErrorMessage())
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(ex: IllegalArgumentException): ApiResponse<Nothing> {
        logger.error("잘못된 요청 발생: ${ex.message}", ex)
        return ResponseFactory.fail("INVALID_REQUEST", ex.message)
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun handleGeneric(ex: Exception): ApiResponse<Nothing> {
        logger.error("알 수 없는 서버 오류 발생: ${ex.message}", ex)
        return ResponseFactory.fail("INTERNAL_ERROR", ex.message)
    }

    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(JwtGenerateTokenException::class)
    fun jwtGenerateTokenException(ex: JwtGenerateTokenException): ApiResponse<Nothing> {
        logger.error("JWT 토큰 생성 오류 발생: ${ex.message}", ex)
        return ResponseFactory.fail(ex.getErrorCode(), ex.getErrorMessage())
    }

    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(AuthorizationDeniedException::class)
    fun authorizationDeniedException(ex: AuthorizationDeniedException): ApiResponse<Nothing> {
        logger.error("JWT 권한 오류 발생: ${ex.message}", ex)
        return ResponseFactory.fail("INTERNAL_ERROR", ex.message)
    }

    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(InvalidJwtFormatException::class)
    fun malformedJwtException(ex: InvalidJwtFormatException): ApiResponse<Nothing> {
        logger.error("JWT 토큰 형식 오류 발생: ${ex.message}", ex)
        return ResponseFactory.fail(ex.getErrorCode(), ex.getErrorMessage())
    }
}