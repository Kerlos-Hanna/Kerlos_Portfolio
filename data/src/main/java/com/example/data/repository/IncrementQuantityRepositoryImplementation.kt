package com.example.data.repository

import com.example.domain.network.NetworkService
import com.example.domain.network.ResultWrapper
import com.example.domain.repository.IncrementQuantityRepository
import com.example.domain.response.update_quantity.UpdateQuantity
import com.example.domain.response.update_quantity.UpdatedProduct

class IncrementQuantityRepositoryImplementation(private val networkService: NetworkService): IncrementQuantityRepository {
    override suspend fun incrementQuantity(updatedProduct: UpdatedProduct): ResultWrapper<String> {
        return networkService.incrementQuantity(updatedProduct)
    }
}