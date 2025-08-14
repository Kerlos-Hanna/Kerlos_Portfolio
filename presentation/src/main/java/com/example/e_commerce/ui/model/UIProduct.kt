package com.example.e_commerce.ui.model

import com.example.domain.model.Product
import kotlinx.serialization.Serializable

@Serializable
data class UIProduct(
    val categoryId: Int,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val title: String
){
    companion object{
        fun fromProduct(product: Product): UIProduct{
            return UIProduct(
                id = product.id,
                categoryId = product.categoryId,
                description = product.description,
                image = product.image,
                price = product.price,
                title = product.title
            )
        }
    }
}


