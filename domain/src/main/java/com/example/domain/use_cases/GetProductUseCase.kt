package com.example.domain.use_cases

import com.example.domain.repository.ProductRepository

class GetProductUseCase(private val repository: ProductRepository) {
    suspend fun getProduct(category: Int) = repository.getProducts(category)
}