package com.example.userapi.domain.exception

import com.example.userapi.common.exception.GlobalErrorCode
import org.springframework.http.HttpStatus


class UserException: RuntimeException {
    lateinit var httpStatus: HttpStatus
    lateinit var errorCode: String
    lateinit var errorMessage: String
    var extraMessage: String? = null

    constructor()

    constructor(userExceptionConst: UserExceptionConst,
                message: String? = null,
                extraMessage: String? = null) :
        super(message ?: "") {
        this.httpStatus = userExceptionConst.httpStatus
        this.errorCode = userExceptionConst.name
        this.errorMessage = userExceptionConst.resultMessage
        this.extraMessage = extraMessage
    }

    constructor(extraMessage: String) : super(extraMessage) {
        this.httpStatus = GlobalErrorCode.BAD_REQUEST_ERROR.httpStatus
        this.errorCode = GlobalErrorCode.BAD_REQUEST_ERROR.name
        this.errorMessage = GlobalErrorCode.BAD_REQUEST_ERROR.resultMessage
        this.extraMessage = extraMessage
    }

}
