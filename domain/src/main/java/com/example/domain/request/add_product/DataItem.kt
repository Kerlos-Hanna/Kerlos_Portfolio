package com.example.domain.request.add_product

import com.example.domain.response.get_product_cart.ProductCartItem
import kotlinx.serialization.Serializable

@Serializable
data class DataItem(
    val id: Int,
    val imageUrl: String,
    val name: String,
    val productId: Int,
    val productName: String,
    val quantity: Int,
    val userId: Int,
){
    companion object{
        fun fromProductCartItem(productCartItem: ProductCartItem): DataItem{
            return DataItem(
                productId = productCartItem.productId,
                productName = productCartItem.productName,
                userId = productCartItem.userId,
                quantity = productCartItem.quantity,
                imageUrl = productCartItem.imageUrl,
                id = productCartItem.id,
                name = productCartItem.name
            )
        }
    }
}