package com.example.userapi.domain.exception

class EmailAlreadyExistException(
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
