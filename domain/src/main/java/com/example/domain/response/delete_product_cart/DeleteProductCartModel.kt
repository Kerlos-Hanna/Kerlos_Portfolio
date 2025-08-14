package com.example.domain.response.delete_product_cart

import kotlinx.serialization.Serializable

@Serializable
data class DeleteProductCartModel(
    val data: List<Data>,
    val msg: String
)