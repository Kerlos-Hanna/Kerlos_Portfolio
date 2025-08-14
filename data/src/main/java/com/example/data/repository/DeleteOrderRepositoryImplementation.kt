package com.example.data.repository

import com.example.domain.network.NetworkService
import com.example.domain.network.ResultWrapper
import com.example.domain.repository.DeleteOrderRepository

class DeleteOrderRepositoryImplementation(val networkService: NetworkService): DeleteOrderRepository {
    override suspend fun deleteOrder(orderID: Int): ResultWrapper<String> {
        return networkService.deleteOrder(orderID)
    }
}