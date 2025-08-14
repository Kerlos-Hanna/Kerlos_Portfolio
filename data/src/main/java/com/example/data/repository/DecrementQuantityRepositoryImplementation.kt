package com.example.data.repository

import com.example.domain.network.NetworkService
import com.example.domain.network.ResultWrapper
import com.example.domain.repository.DecrementQuantityRepository
import com.example.domain.response.update_quantity.UpdatedProduct

class DecrementQuantityRepositoryImplementation(private val networkService: NetworkService): DecrementQuantityRepository {
    override suspend fun decrementQuantity(updatedProduct: UpdatedProduct): ResultWrapper<String> {
        return networkService.decrementQuantity(updatedProduct)
    }
}