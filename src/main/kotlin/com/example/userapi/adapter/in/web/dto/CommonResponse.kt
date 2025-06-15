package com.example.userapi.adapter.`in`.web.dto

import com.example.userapi.common.exception.CommonException
import com.example.userapi.common.exception.SystemCodeMap
import com.example.userapi.domain.exception.UserException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

data class ApiResponse<T>(
    val result: Result,
    val data: T? = null
)

data class Result(
    val code: String,
    val message: String?
)

object ResponseFactory {
/*    fun <T> success(data: T): ApiResponse<T> =
        ApiResponse(Result("SUCCESS", "요청이 성공했습니다."), data)*/

    fun <T> success(data: T): ResponseEntity<ApiResponse<T>> =
        ResponseEntity
            .ok()
            .body(
                ApiResponse(
                    Result(
                        SystemCodeMap.STATUS_SUCCESS.getSystemCode(),
                        SystemCodeMap.STATUS_SUCCESS.getSystemErrorMessage()
                    ),
                    data
                )
            )

    fun fail(code: String, message: String?): ApiResponse<Nothing> =
        ApiResponse(Result(code, message), null)

    fun fail(exception: UserException?): ResponseEntity<ApiResponse<Nothing>> =
        ResponseEntity
            .status(exception?.httpStatus ?: HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ApiResponse(
                    Result(
                        exception?.errorCode ?: SystemCodeMap.STATUS_FAIL.getSystemCode(),
                        exception?.errorMessage ?: SystemCodeMap.STATUS_FAIL.getSystemErrorMessage()
                    ),null
                )
            )

    fun fail(exception: CommonException?): ResponseEntity<ApiResponse<Nothing>> =
        ResponseEntity
            .status(exception?.httpStatus ?: HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ApiResponse(
                    Result(
                        exception?.errorCode ?: SystemCodeMap.STATUS_FAIL.getSystemCode(),
                        exception?.errorMessage ?: SystemCodeMap.STATUS_FAIL.getSystemErrorMessage()
                    ),null
                )
            )
}
