package com.example.data.model.product_response

import kotlinx.serialization.Serializable

@Serializable
data class ListDataResponseModel(
    val `data`: List<DataProductModel>,
    val msg: String
)