package com.example.domain.response.user

import kotlinx.serialization.Serializable

@Serializable
data class UserResponseModel(
    val data: UserResponse,
    val msg: String
)