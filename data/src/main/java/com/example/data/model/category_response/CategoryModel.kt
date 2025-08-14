package com.example.data.model.category_response

import com.example.domain.model.Category
import kotlinx.serialization.Serializable

@Serializable
data class CategoryModel(
    val id: Int,
    val image: String,
    val title: String
){
    fun toCategory(): Category{
        return Category(
            id = id,
            image = image,
            title = title
        )
    }
}