package com.example.domain.response.get_product_cart

import kotlinx.serialization.Serializable

@Serializable
data class ProductCartModelResponse(
    val data: List<ProductCartItem>,
    val msg: String
)