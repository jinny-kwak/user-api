package com.example.userapi.common.exception

enum class CommonExceptionConst(val message: String) {
    TOKEN_GENERATE_ERROR("토큰 생성 오류가 발생했습니다."),
    TOKEN_FORMAT_ERROR("토큰 형식이 잘못되었습니다."),
    NO_UPDATE_PERMISSION("수정 권한이 없습니다. 본인 계정 및 권한을 확인해주세요.");

    companion object {
        fun getByCode(name: String): CommonExceptionConst? {
            return CommonExceptionConst.entries.find { it.name == name }
        }
    }
}