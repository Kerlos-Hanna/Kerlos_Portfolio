package com.example.domain.response.get_product_cart

import kotlinx.serialization.Serializable

@Serializable
data class ProductCartItem(
    val id: Int,
    val imageUrl: String,
    val name: String,
    val price: Float,
    val productId: Int,
    val productName: String,
    var quantity: Int,
    val userId: Int
)