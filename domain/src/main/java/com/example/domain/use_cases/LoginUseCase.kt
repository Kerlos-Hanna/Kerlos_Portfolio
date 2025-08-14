package com.example.domain.use_cases

import com.example.domain.repository.UserRepository

class LoginUseCase(private val userRepository: UserRepository) {
    suspend fun login(email: String , password: String) = userRepository.login(email , password)
}