package com.example.data.repository

import com.example.domain.network.NetworkService
import com.example.domain.network.ResultWrapper
import com.example.domain.repository.ProductToCartRepository
import com.example.domain.request.add_product.AddCartRequestModel

class ProductToCartRepositoryImplementation(private val networkService: NetworkService): ProductToCartRepository {
    override suspend fun addProductToCart(requestModel: AddCartRequestModel): ResultWrapper<String> {
        return networkService.addProductToCart(requestModel)
    }
}