package com.example.userapi.common.exception

import com.example.userapi.adapter.`in`.web.dto.ApiResponse
import com.example.userapi.adapter.`in`.web.dto.ResponseFactory
import com.example.userapi.domain.exception.UserException
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.security.authorization.AuthorizationDeniedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
internal class GlobalExceptionHandler {

    private val logger: Logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

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
    @ExceptionHandler(AuthorizationDeniedException::class)
    fun authorizationDeniedException(ex: AuthorizationDeniedException, request: HttpServletRequest): ApiResponse<Nothing> {
        logger.error("접근 권한 오류 발생: ${ex.javaClass.name} ${request.requestURI}")
        logger.error("${ex.message}", ex)
        return ResponseFactory.fail(CommonExceptionConst.AUTH_DENIED_ERROR.name, CommonExceptionConst.AUTH_DENIED_ERROR.resultMessage)
    }

    @ExceptionHandler(UserException::class)
    fun handleUserDomainException(ex: UserException, request: HttpServletRequest): ResponseEntity<ApiResponse<Nothing>> {
        logger.error("존재하지 않는 사용자 발생: ${ex.javaClass.name} ${request.requestURI}")
        logger.error("${ex.message}", ex)
        return ResponseFactory.fail(ex)
    }

    @ExceptionHandler(CommonException::class)
    fun commonException(ex: CommonException, request: HttpServletRequest): ResponseEntity<ApiResponse<Nothing>> {
        logger.error("공통 오류 발생: ${ex.javaClass.name} ${request.requestURI}")
        logger.error("${ex.message}", ex)
        return ResponseFactory.fail(ex)
    }


}