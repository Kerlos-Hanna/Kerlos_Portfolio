package com.example.domain.response.update_quantity

import com.example.domain.request.add_product.DataItem
import kotlinx.serialization.Serializable

@Serializable
data class UpdatedProduct(
    val id: Int,
    val imageUrl: String,
    val name: String,
    val productId: Int,
    val productName: String,
    val quantity: Int,
    val userId: Int
){
    companion object{
        fun fromDataItem(dataItem: DataItem): UpdatedProduct{
            return UpdatedProduct(
                productId = dataItem.productId,
                productName = dataItem.productName,
                id = dataItem.id,
                name = dataItem.name,
                userId = dataItem.userId,
                quantity = dataItem.quantity,
                imageUrl = dataItem.imageUrl
            )
        }
    }
}