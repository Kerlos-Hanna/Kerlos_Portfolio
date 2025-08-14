package com.example.data.repository

import com.example.domain.network.NetworkService
import com.example.domain.network.ResultWrapper
import com.example.domain.repository.MakeOrderRepository
import com.example.domain.request.order.AddressDataDomain

class MakeOrderRepositoryImplementation(val networkService: NetworkService): MakeOrderRepository {
    override suspend fun makeOrder(addressDataDomain: AddressDataDomain): ResultWrapper<Long> {
        return networkService.makeOrder(addressDataDomain)
    }
}