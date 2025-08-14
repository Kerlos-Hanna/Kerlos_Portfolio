package com.example.e_commerce.ui.model

import android.os.Parcelable
import com.example.domain.request.order.AddressDataDomain
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class AddressData(
    val addressLine: String,
    val government: String,
    val postalCode: String,
    val country: String
):Parcelable{
    companion object{
        fun fromAddressData(addressData: AddressData): AddressDataDomain{
            return AddressDataDomain(
                addressLine = addressData.addressLine,
                country = addressData.country,
                postalCode = addressData.postalCode,
                city = addressData.government,
                state = addressData.government
            )
        }
    }
}
