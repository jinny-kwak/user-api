package com.example.userapi.common.exception

class ForbiddenAccessException(
    val exceptionConst: CommonExceptionConst,
    message: String = exceptionConst.message
) : RuntimeException(message) {

    fun getErrorCode(): String {
        return exceptionConst.name
    }

    fun getErrorMessage(): String {
        return exceptionConst.message
    }
}
