package com.example.domain.response.get_orders

import kotlinx.serialization.Serializable

@Serializable
data class GetOrdersResponseModel(
    val `data`: List<Order>,
    val msg: String
)