package com.example.userapi.domain.dto

import com.example.userapi.adapter.`in`.web.dto.UserAdapterDtoV2

class UserPortDtoV2 {
    class In {
        data class SignUpRequest(
            val email: String,
            val password: String,
            val name: String,
            val phone: String,
        )


        companion object {
            fun from(adapterDtoV2: UserAdapterDtoV2.In.SignUpRequest): SignUpRequest {
                return SignUpRequest(
                    email = adapterDtoV2.email,
                    password = adapterDtoV2.password,
                    name = adapterDtoV2.name,
                    phone = adapterDtoV2.phone
                )
            }
        }

    }


}