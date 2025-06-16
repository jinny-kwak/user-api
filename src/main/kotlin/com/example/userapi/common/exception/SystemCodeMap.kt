package com.example.userapi.common.exception

enum class SystemCodeMap(
    private val systemCode : String,
    private val systemMessage : String
) {
    STATUS_SUCCESS("SUCCESS", "요청을 성공했습니다."),
    STATUS_FAIL("FAIL", "요청을 실패했습니다.")
    ;

    fun getSystemCode(): String {
        return this.systemCode
    }

    fun getSystemErrorMessage(): String {
        return this.systemMessage
    }

}