package com.example.userapi.common.exception

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.UNAUTHORIZED

enum class CommonExceptionConst(
    override val httpStatus: HttpStatus,
    override val resultMessage: String
) : ErrorCode {
    AUTH_DENIED_ERROR(UNAUTHORIZED, "접근 권한이 없습니다. 본인 계정 및 권한을 확인해 주세요."),
    TOKEN_GENERATE_ERROR(UNAUTHORIZED,"토큰 생성 오류가 발생했습니다."),
    TOKEN_FORMAT_ERROR(UNAUTHORIZED,"토큰 형식이 잘못되었습니다."),
    NO_UPDATE_PERMISSION(UNAUTHORIZED, "수정 권한이 없습니다. 본인 계정 및 권한을 확인해 주세요.")

    ;
}