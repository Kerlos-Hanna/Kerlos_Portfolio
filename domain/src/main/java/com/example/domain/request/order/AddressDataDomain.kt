package com.example.domain.request.order

data class AddressDataDomain(
    val addressLine: String,
    val city: String,
    val state: String,
    val postalCode: String,
    val country: String
)
