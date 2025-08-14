package com.example.domain.request.register

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequestModel(
    val email: String,
    val name: String,
    val password: String
)