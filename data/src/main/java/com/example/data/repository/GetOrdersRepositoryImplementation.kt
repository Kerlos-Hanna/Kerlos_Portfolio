package com.example.data.repository

import com.example.domain.network.NetworkService
import com.example.domain.network.ResultWrapper
import com.example.domain.repository.GetOrdersRepository
import com.example.domain.response.get_orders.Order

class GetOrdersRepositoryImplementation(private val networkService: NetworkService): GetOrdersRepository {
    override suspend fun getOrders(): ResultWrapper<List<Order>>{
        return networkService.getOrders()
    }
}