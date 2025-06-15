package com.example.userapi.common.exception

import com.example.userapi.domain.exception.UserExceptionConst
import org.springframework.http.HttpStatus

interface ErrorCode {
    val httpStatus: HttpStatus
    val resultMessage: String
}