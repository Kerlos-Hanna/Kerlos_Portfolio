package com.example.domain.response.get_orders

import kotlinx.serialization.Serializable

@Serializable
data class Address(
    val addressLine: String,
    val city: String,
    val country: String,
    val postalCode: String,
    val state: String
)