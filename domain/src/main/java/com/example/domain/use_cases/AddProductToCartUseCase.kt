package com.example.domain.use_cases

import com.example.domain.repository.ProductToCartRepository
import com.example.domain.request.add_product.AddCartRequestModel

class AddProductToCartUseCase(private val repository: ProductToCartRepository) {
    suspend fun addProductToCart(requestModel: AddCartRequestModel) = repository.addProductToCart(requestModel)
}