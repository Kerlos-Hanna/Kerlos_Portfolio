package com.example.e_commerce.navigation

import com.example.e_commerce.ui.model.AddressData
import com.example.e_commerce.ui.model.UIProduct
import kotlinx.serialization.Serializable

@Serializable
object HomeScreen

@Serializable
object CartScreen

@Serializable
object ProfileScreen

@Serializable
data class ProductDetails(
    val product: UIProduct
)

@Serializable
data class CheckOutScreen(
    val addressData: AddressData
)

@Serializable
object AddressScreen

@Serializable
object OrdersScreen

@Serializable
object RegisterScreen

@Serializable
object LoginScreen