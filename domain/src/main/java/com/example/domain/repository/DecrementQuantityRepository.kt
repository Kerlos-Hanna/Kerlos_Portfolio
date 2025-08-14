package com.example.domain.repository

import com.example.domain.network.ResultWrapper
import com.example.domain.response.update_quantity.UpdatedProduct

interface DecrementQuantityRepository {
    suspend fun decrementQuantity(updatedProduct: UpdatedProduct): ResultWrapper<String>
}