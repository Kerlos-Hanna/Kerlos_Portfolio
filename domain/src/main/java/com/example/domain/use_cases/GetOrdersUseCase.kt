package com.example.domain.use_cases

import com.example.domain.repository.GetOrdersRepository

class GetOrdersUseCase(private val getOrdersRepository: GetOrdersRepository) {
    suspend fun getOrders() = getOrdersRepository.getOrders()
}