package com.example.domain.repository

import com.example.domain.network.ResultWrapper
import com.example.domain.response.user.UserResponse
import com.example.domain.response.user.UserResponseModel

interface UserRepository {
    suspend fun login(email: String , password: String): ResultWrapper<UserResponse>

    suspend fun register(email: String , password: String , name: String): ResultWrapper<UserResponse>
}