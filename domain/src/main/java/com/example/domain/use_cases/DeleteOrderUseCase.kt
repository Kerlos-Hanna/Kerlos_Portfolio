package com.example.domain.use_cases

import com.example.domain.repository.DeleteOrderRepository

class DeleteOrderUseCase(private val deleteOrderRepository: DeleteOrderRepository) {
    suspend fun deleteOrder(orderID: Int) = deleteOrderRepository.deleteOrder(orderID)
}