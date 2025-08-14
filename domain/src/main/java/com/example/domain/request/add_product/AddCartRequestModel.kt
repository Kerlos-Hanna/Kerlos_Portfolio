package com.example.domain.request.add_product

import kotlinx.serialization.Serializable

@Serializable
data class AddCartRequestModel(
    val imageUrl: String,
    val price: Int,
    val productId: Int,
    val productName: String,
    val quantity: Int,
    val userId: Int
)