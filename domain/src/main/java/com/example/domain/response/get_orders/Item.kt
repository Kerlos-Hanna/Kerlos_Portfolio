package com.example.domain.response.get_orders

import kotlinx.serialization.Serializable

@Serializable
data class Item(
    val id: Int,
    val orderId: Int,
    val price: Double,
    val productId: Int,
    val productName: String,
    val quantity: Int,
    val userId: Int
)