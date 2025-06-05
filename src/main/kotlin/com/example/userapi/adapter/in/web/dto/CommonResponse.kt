package com.example.userapi.adapter.`in`.web.dto

data class ApiResponse<T>(
    val result: Result,
    val data: T? = null
)

data class Result(
    val code: String,
    val message: String
)

object ResponseFactory {
    fun <T> success(data: T): ApiResponse<T> =
        ApiResponse(Result("SUCCESS", "요청이 성공했습니다."), data)

    fun fail(code: String, message: String): ApiResponse<Nothing> =
        ApiResponse(Result(code, message), null)
}
