package com.example.domain.repository

import com.example.domain.network.ResultWrapper
import com.example.domain.response.get_orders.Order

interface GetOrdersRepository {
    suspend fun getOrders(): ResultWrapper<List<Order>>
}