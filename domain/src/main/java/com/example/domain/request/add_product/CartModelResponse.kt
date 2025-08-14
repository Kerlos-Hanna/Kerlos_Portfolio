package com.example.domain.request.add_product

import kotlinx.serialization.Serializable

@Serializable
data class CartModelResponse(
    val data: List<DataItem>,
    val msg: String
)