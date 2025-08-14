package com.example.domain.repository

import com.example.domain.network.ResultWrapper
import com.example.domain.response.get_product_cart.ProductCartItem

interface ProductCartRepository {
    suspend fun getProductCart(userID: Int): ResultWrapper<List<ProductCartItem>>
}