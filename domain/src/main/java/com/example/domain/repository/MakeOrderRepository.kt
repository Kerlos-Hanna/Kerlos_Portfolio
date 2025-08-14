package com.example.domain.repository

import com.example.domain.network.ResultWrapper
import com.example.domain.request.order.AddressDataDomain

interface MakeOrderRepository {
    suspend fun makeOrder(addressDataDomain: AddressDataDomain): ResultWrapper<Long>
}