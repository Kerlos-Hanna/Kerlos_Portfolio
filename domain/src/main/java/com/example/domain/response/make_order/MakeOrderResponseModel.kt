package com.example.domain.response.make_order

import kotlinx.serialization.Serializable

@Serializable
data class MakeOrderResponseModel(
    val msg: String,
    //val `data`: OrderIDResponse
)
