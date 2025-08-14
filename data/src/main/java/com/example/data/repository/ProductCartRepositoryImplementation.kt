package com.example.data.repository

import com.example.domain.network.NetworkService
import com.example.domain.network.ResultWrapper
import com.example.domain.repository.ProductCartRepository
import com.example.domain.response.get_product_cart.ProductCartItem

class ProductCartRepositoryImplementation(private val networkService: NetworkService): ProductCartRepository {
    override suspend fun getProductCart(userID: Int): ResultWrapper<List<ProductCartItem>> {
        return networkService.getProductCart(userID)
    }

}