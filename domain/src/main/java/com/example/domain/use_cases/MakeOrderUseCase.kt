package com.example.domain.use_cases

import com.example.domain.repository.MakeOrderRepository
import com.example.domain.request.order.AddressDataDomain

class MakeOrderUseCase(private val makeOrderRepository: MakeOrderRepository) {
    suspend fun makeOrder(addressDataDomain: AddressDataDomain) = makeOrderRepository.makeOrder(addressDataDomain)
}