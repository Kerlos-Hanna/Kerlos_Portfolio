package com.example.data.model.category_response

import kotlinx.serialization.Serializable

@Serializable
data class ListCategoryResponseModel(
    val `data`: List<CategoryModel>,
    val msg: String
)