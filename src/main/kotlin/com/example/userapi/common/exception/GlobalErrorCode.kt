package com.example.userapi.common.exception

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*

enum class GlobalErrorCode(
    override val httpStatus: HttpStatus,
    override val resultMessage: String
) : ErrorCode {
    BAD_REQUEST_ERROR(BAD_REQUEST, "Bad Request."),
    INVALID_PARAMETER(BAD_REQUEST, "The Parameter value is not invalid."),
    DATA_NOT_FOUND(NOT_FOUND, "No data found."),
    SERVER_ERROR(INTERNAL_SERVER_ERROR, "Error has occurred between core servers."),
    HTTP_HEADER_REQUIRED(BAD_REQUEST, "There is no necessary header values."),
    INVALID_HEADER_SERVICE(BAD_REQUEST, "The service header value is not supported.")
}