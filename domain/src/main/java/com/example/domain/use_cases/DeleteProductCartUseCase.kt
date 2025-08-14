package com.example.domain.use_cases

import com.example.domain.network.ResultWrapper
import com.example.domain.repository.DeleteProductCartRepository

class DeleteProductCartUseCase(private val deleteProductCartRepository: DeleteProductCartRepository) {
    suspend fun deleteProductCartUseCase(productID: Int): ResultWrapper<String> {
        return deleteProductCartRepository.deleteProductFromCart(productID)
    }
}