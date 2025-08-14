package com.example.data.repository

import android.util.Log
import com.example.domain.network.NetworkService
import com.example.domain.network.ResultWrapper
import com.example.domain.repository.DeleteProductCartRepository

class DeleteProductCartRepositoryImplementation(private val networkService: NetworkService): DeleteProductCartRepository {
    override suspend fun deleteProductFromCart(productId: Int): ResultWrapper<String> {
        Log.d("mmm" , "delete  Repository Impl")
        return networkService.deleteProductCart(productId)
    }
}