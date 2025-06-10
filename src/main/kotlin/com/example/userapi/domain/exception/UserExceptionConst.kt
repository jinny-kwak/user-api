package com.example.userapi.domain.exception

enum class UserExceptionConst(val message: String) {
    NOT_EXISTS_USER("가입된 사용자가 존재하지 않습니다.");

    companion object {
        fun getByCode(name: String): UserExceptionConst? {
            return UserExceptionConst.entries.find { it.name == name }
        }
    }
}