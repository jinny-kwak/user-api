package com.example.userapi.util

val EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)\$".toRegex()
val PASSWORD_REGEX = "^[A-Za-z0-9!@#$%^&*()_+\\-={}\\[\\]:;\"'<>,.?/`~|\\\\]{12,}$".toRegex()

fun isValidEmail(email: String): Boolean {
    return email.matches(EMAIL_REGEX)
}

fun isValidPassword(password: String): Boolean {
    // todo refactoring 필요
    // 기본 패턴 체크
    // 대문자, 소문자, 숫자, 특수문자 포함한 12자리 이상 문자열만 허용
    if (!PASSWORD_REGEX.matches(password)) {
        return false
    }

    // 문자 종류별 패턴
    val upperCasePattern = Regex("[A-Z]")
    val lowerCasePattern = Regex("[a-z]")
    val digitPattern = Regex("[0-9]")
    val specialCharPattern = Regex("[!@#$%^&*()_+\\-={}\\[\\]:;\"'<>,.?/`~|\\\\]")

    // 몇 가지 종류가 포함되어 있는지 확인
    var count = 0
    if (lowerCasePattern.containsMatchIn(password)) count++
    if (upperCasePattern.containsMatchIn(password)) count++
    if (digitPattern.containsMatchIn(password)) count++
    if (specialCharPattern.containsMatchIn(password)) count++

    return count >= 3
}