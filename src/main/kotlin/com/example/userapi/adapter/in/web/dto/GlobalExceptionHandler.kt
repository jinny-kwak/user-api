package com.example.userapi.adapter.`in`.web

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import com.example.userapi.adapter.`in`.web.dto.ApiResponse
import com.example.userapi.adapter.`in`.web.dto.ResponseFactory

@RestControllerAdvice
class GlobalExceptionHandler {

    private val logger: Logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(ex: IllegalArgumentException): ApiResponse<Nothing> {
        logger.error("잘못된 요청 발생: ${ex.message}", ex)  // 에러 로그 출력
        return ResponseFactory.fail("INVALID_REQUEST", ex.message ?: "잘못된 요청입니다.")
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneric(ex: Exception): ApiResponse<Nothing> {
        logger.error("알 수 없는 오류 발생: ${ex.message}", ex)  // 에러 로그 출력
        return ResponseFactory.fail("INTERNAL_ERROR", "알 수 없는 오류가 발생했습니다.")
    }
}
