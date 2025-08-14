package com.example.domain.use_cases

import com.example.domain.repository.DecrementQuantityRepository
import com.example.domain.response.update_quantity.UpdatedProduct

class DecrementQuantityUseCase(private val decrementQuantityRepository: DecrementQuantityRepository) {
    suspend fun decrementQuantity(updatedProduct: UpdatedProduct) = decrementQuantityRepository.decrementQuantity(updatedProduct)
}