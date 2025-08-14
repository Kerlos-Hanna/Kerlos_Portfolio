package com.example.domain.use_cases

import com.example.domain.repository.IncrementQuantityRepository
import com.example.domain.response.update_quantity.UpdatedProduct

class IncrementQuantityUseCase(private val incrementQuantityRepository: IncrementQuantityRepository) {
    suspend fun incrementQuantity(updatedProduct: UpdatedProduct) = incrementQuantityRepository.incrementQuantity(updatedProduct)
}