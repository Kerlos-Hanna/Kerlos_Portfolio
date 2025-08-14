package com.example.domain.repository

import com.example.domain.network.ResultWrapper
import com.example.domain.response.update_quantity.UpdatedProduct

interface IncrementQuantityRepository {
    suspend fun incrementQuantity(updatedProduct: UpdatedProduct): ResultWrapper<String>
}