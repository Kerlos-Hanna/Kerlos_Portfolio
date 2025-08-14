package com.example.domain.repository

import com.example.domain.network.ResultWrapper
import com.example.domain.request.add_product.AddCartRequestModel

interface ProductToCartRepository {
    suspend fun addProductToCart(requestModel: AddCartRequestModel): ResultWrapper<String>
}