package com.example.data.model

import com.example.domain.request.order.AddressDataDomain
import kotlinx.serialization.Serializable

@Serializable
data class AddressDataData (
    val addressLine: String,
    val city: String,
    val state: String,
    val postalCode: String,
    val country: String
){
    companion object{
        fun fromAddressDataDomain(addressDataDomain: AddressDataDomain): AddressDataData{
            return AddressDataData(
                addressLine = addressDataDomain.addressLine,
                city = addressDataDomain.city,
                state = addressDataDomain.state,
                postalCode = addressDataDomain.postalCode,
                country = addressDataDomain.country
            )
        }
    }
}