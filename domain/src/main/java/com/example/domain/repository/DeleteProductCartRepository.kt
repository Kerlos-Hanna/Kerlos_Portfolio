package com.example.domain.repository

import com.example.domain.network.ResultWrapper

interface DeleteProductCartRepository {
    suspend fun deleteProductFromCart(productId: Int): ResultWrapper<String>
}