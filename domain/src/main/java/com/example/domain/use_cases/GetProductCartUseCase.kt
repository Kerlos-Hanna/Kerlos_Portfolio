package com.example.domain.use_cases

import com.example.domain.repository.ProductCartRepository

class GetProductCartUseCase(val repository: ProductCartRepository) {
    suspend fun getProductCart(userID: Int) = repository.getProductCart(userID)
}