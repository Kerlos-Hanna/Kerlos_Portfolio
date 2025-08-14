package com.example.data.repository

import com.example.domain.model.Category
import com.example.domain.network.NetworkService
import com.example.domain.network.ResultWrapper
import com.example.domain.repository.CategoryRepository

class CategoryRepositoryImplementation(private val networkService: NetworkService): CategoryRepository {
    override suspend fun getCategories(): ResultWrapper<List<Category>> {
        return networkService.getCategories()
    }
}