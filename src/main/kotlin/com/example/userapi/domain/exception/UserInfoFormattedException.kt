package com.example.userapi.domain.exception

class UserInfoFormattedException(
    val exceptionConst: UserExceptionConst,
    message: String = exceptionConst.message
) : RuntimeException(message) {

    fun getErrorCode(): String {
        return exceptionConst.name
    }

    fun getErrorMessage(): String {
        return exceptionConst.message
    }
}
