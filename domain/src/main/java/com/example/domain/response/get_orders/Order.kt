package com.example.domain.response.get_orders

import kotlinx.serialization.Serializable

@Serializable
data class Order(
    val address: Address,
    val id: Int,
    val items: List<Item>,
    val orderDate: String,
    val status: String,
    val totalAmount: Double,
    val userId: Int
)