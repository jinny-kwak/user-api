package com.example.userapi.domain.exception

import com.example.userapi.common.exception.ErrorCode
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*

enum class UserExceptionConst(
    override val httpStatus: HttpStatus,
    override val resultMessage: String
) : ErrorCode {
    NOT_EXISTS_USER(NOT_FOUND, "가입된 사용자가 존재하지 않습니다."),
    NOT_FORMATTED_EMAIL(BAD_REQUEST,"이메일 형식이 올바르지 않습니다. 이메일 정규식을 확인해주세요."),
    NOT_FORMATTED_PASSWORD(BAD_REQUEST,"비밀번호 형식이 올바르지 않습니다. 영어 대문자, 영어 소문자, 숫자, 특수문자 중 3종류 이상으로 12자리 이상의 문자열 이어야 합니다."),
    ALREADY_EXISTS_EMAIL(NOT_FOUND,"이미 존재하는 이메일입니다."),
    INVALID_PASSWORD(BAD_REQUEST,"비밀번호가 일치하지 않습니다.")
    ;

}