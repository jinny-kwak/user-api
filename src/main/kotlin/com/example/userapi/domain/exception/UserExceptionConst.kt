package com.example.userapi.domain.exception

enum class UserExceptionConst(val message: String) {
    NOT_EXISTS_USER("가입된 사용자가 존재하지 않습니다."),
    NOT_FORMATTED_EMAIL("이메일 형식이 올바르지 않습니다. 이메일 정규식을 확인해주세요."),
    NOT_FORMATTED_PASSWORD("비밀번호 형식이 올바르지 않습니다. 영어 대문자, 영어 소문자, 숫자, 특수문자 중 3종류 이상으로 12자리 이상의 문자열 이어야 합니다."),
    ALREADY_EXISTS_EMAIL("이미 존재하는 이메일입니다."),
    INVALID_PASSWORD("비밀번호가 일치하지 않습니다.")
    ;

    companion object {
        fun getByCode(name: String): UserExceptionConst? {
            return UserExceptionConst.entries.find { it.name == name }
        }
    }
}