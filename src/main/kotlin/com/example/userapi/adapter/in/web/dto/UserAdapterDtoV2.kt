package com.example.userapi.adapter.`in`.web.dto

class UserAdapterDtoV2 {
    class In {
        data class SignUpRequest(
            val email: String,
            val password: String,
            val name: String,
            val phone: String,
        )

    }

}