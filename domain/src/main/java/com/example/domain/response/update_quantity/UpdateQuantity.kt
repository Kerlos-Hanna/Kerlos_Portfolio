package com.example.domain.response.update_quantity

import kotlinx.serialization.Serializable

@Serializable
data class UpdateQuantity(
    val data: List<UpdatedProduct>,
    val msg: String
)