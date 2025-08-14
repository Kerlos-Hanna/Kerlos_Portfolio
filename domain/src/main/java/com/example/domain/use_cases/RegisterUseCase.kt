package com.example.domain.use_cases

import com.example.domain.repository.UserRepository

class RegisterUseCase(private val userRepository: UserRepository) {
    suspend fun register(email: String , password: String , name: String) = userRepository.register(email , password , name)
}