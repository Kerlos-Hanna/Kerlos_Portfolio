package com.example.domain.use_cases

import com.example.domain.repository.CategoryRepository

class GetCategoryUseCase(private val repository: CategoryRepository) {
    suspend fun getCategories() = repository.getCategories()
}