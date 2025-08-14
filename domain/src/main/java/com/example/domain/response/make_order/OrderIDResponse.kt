package com.example.domain.response.make_order

import kotlinx.serialization.Serializable

@Serializable
data class OrderIDResponse(
    val orderID: Long
)