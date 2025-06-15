package com.example.userapi.common.exception

import org.springframework.http.HttpStatus

class CommonException: RuntimeException {
    lateinit var httpStatus: HttpStatus
    lateinit var errorCode: String
    lateinit var errorMessage: String
    var extraMessage: String? = null

    constructor()

    constructor(commonExceptionConst: CommonExceptionConst,
                message: String? = null,
                extraMessage: String? = null) :
        super(message ?: "") {
        this.httpStatus = commonExceptionConst.httpStatus
        this.errorCode = commonExceptionConst.name
        this.errorMessage = commonExceptionConst.resultMessage
        this.extraMessage = extraMessage
    }

    constructor(extraMessage: String) : super(extraMessage) {
        this.httpStatus = GlobalErrorCode.BAD_REQUEST_ERROR.httpStatus
        this.errorCode = GlobalErrorCode.BAD_REQUEST_ERROR.name
        this.errorMessage = GlobalErrorCode.BAD_REQUEST_ERROR.resultMessage
        this.extraMessage = extraMessage
    }

}
