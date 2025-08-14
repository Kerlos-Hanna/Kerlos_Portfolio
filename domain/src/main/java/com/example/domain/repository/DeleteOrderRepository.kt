package com.example.domain.repository

import com.example.domain.network.ResultWrapper

interface DeleteOrderRepository {
    suspend fun deleteOrder(orderID: Int): ResultWrapper<String>
}