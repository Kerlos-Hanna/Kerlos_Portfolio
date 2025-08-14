package com.example.domain.network

import com.example.domain.model.Category
import com.example.domain.model.Product
import com.example.domain.request.add_product.AddCartRequestModel
import com.example.domain.request.add_product.CartModelResponse
import com.example.domain.request.add_product.DataItem
import com.example.domain.request.order.AddressDataDomain
import com.example.domain.response.get_orders.Order
import com.example.domain.response.get_product_cart.ProductCartItem
import com.example.domain.response.update_quantity.UpdateQuantity
import com.example.domain.response.update_quantity.UpdatedProduct
import com.example.domain.response.user.UserResponse
import com.example.domain.response.user.UserResponseModel
import java.lang.Exception

interface NetworkService {
    suspend fun getProducts(category: Int): ResultWrapper<List<Product>>
    suspend fun getCategories(): ResultWrapper<List<Category>>
    suspend fun addProductToCart(requestModel: AddCartRequestModel): ResultWrapper<String>
    suspend fun getProductCart(userID: Int): ResultWrapper<List<ProductCartItem>>
    suspend fun deleteProductCart(productID: Int): ResultWrapper<String>
    suspend fun incrementQuantity(updatedProduct: UpdatedProduct): ResultWrapper<String>
    suspend fun decrementQuantity(updatedProduct: UpdatedProduct): ResultWrapper<String>
    suspend fun makeOrder(addressDataDomain: AddressDataDomain): ResultWrapper<Long>
    suspend fun getOrders(): ResultWrapper<List<Order>>
    suspend fun deleteOrder(orderID: Int): ResultWrapper<String>
    suspend fun login(email: String , password: String): ResultWrapper<UserResponse>
    suspend fun register(email: String , password: String , name: String): ResultWrapper<UserResponse>
}

sealed class ResultWrapper<out T>{
    data class Success<out T>(val value: T): ResultWrapper<T>()
    data class Failure(val exception: Exception): ResultWrapper<Nothing>()
}