package com.example.data.network

import android.content.Context
import android.util.Log
import com.example.data.model.AddressDataData
import com.example.data.model.category_response.ListCategoryResponseModel
import com.example.data.model.product_response.ListDataResponseModel
import com.example.domain.model.Category
import com.example.domain.model.Product
import com.example.domain.network.NetworkService
import com.example.domain.network.ResultWrapper
import com.example.domain.request.add_product.AddCartRequestModel
import com.example.domain.request.add_product.CartModelResponse
import com.example.domain.request.login.LoginRequestModel
import com.example.domain.request.order.AddressDataDomain
import com.example.domain.request.register.RegisterRequestModel
import com.example.domain.response.delete_product_cart.DeleteProductCartModel
import com.example.domain.response.get_orders.GetOrdersResponseModel
import com.example.domain.response.get_orders.Order
import com.example.domain.response.get_product_cart.ProductCartItem
import com.example.domain.response.get_product_cart.ProductCartModelResponse
import com.example.domain.response.make_order.MakeOrderResponseModel
import com.example.domain.response.update_quantity.UpdateQuantity
import com.example.domain.response.update_quantity.UpdatedProduct
import com.example.domain.response.user.UserResponse
import com.example.domain.response.user.UserResponseModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import io.ktor.http.contentType
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.IOException

class NetworkServiceImplementation(private val client: HttpClient): NetworkService , KoinComponent {

    private val context: Context by inject()
    private val sharedPreferences = context.getSharedPreferences("user" , Context.MODE_PRIVATE)

    override suspend fun getProducts(category: Int): ResultWrapper<List<Product>> {

        return makeWebRequest<ListDataResponseModel, List<Product>>(
            url = "https://ecommerce-ktor-4641e7ff1b63.herokuapp.com/products/category/$category",
            method = HttpMethod.Get,
            mapper = { dataModels: ListDataResponseModel ->
                dataModels.data.map { it.toProduct() }
            }
        )
    }

    override suspend fun getCategories(): ResultWrapper<List<Category>> {
        return makeWebRequest<ListCategoryResponseModel , List<Category>>(
            url = "https://ecommerce-ktor-4641e7ff1b63.herokuapp.com/categories",
            method = HttpMethod.Get,
            mapper = {categoryModel: ListCategoryResponseModel ->
                categoryModel.data.map { it.toCategory() }
            }
        )
    }

    override suspend fun addProductToCart(requestModel: AddCartRequestModel): ResultWrapper<String> {

        Log.d("mmm" , requestModel.userId.toString())

        return makeWebRequest<CartModelResponse , String>(
            url = "https://ecommerce-ktor-4641e7ff1b63.herokuapp.com/cart/${requestModel.userId}",
            method = HttpMethod.Post,
            mapper = {cartModelResponse ->
                cartModelResponse.msg
            },
            body = requestModel
        )
    }

    override suspend fun getProductCart(userID: Int): ResultWrapper<List<ProductCartItem>> {

        Log.d("mmm" , userID.toString())

        return makeWebRequest<ProductCartModelResponse , List<ProductCartItem>>(
            url = "https://ecommerce-ktor-4641e7ff1b63.herokuapp.com/cart/$userID",
            method = HttpMethod.Get,
            mapper = {productCartModelResponse: ProductCartModelResponse ->
                productCartModelResponse.data
            }
        )
    }

    override suspend fun deleteProductCart(productID: Int): ResultWrapper<String> {
        return makeWebRequest<DeleteProductCartModel , String>(
            url = "https://ecommerce-ktor-4641e7ff1b63.herokuapp.com/cart/${sharedPreferences.getInt("id" , 0)}/$productID",
            method = HttpMethod.Delete,
            mapper = {deleteProductCartModel ->
                deleteProductCartModel.msg
            }
        )
    }

    override suspend fun incrementQuantity(updatedProduct: UpdatedProduct): ResultWrapper<String> {
        return makeWebRequest<UpdateQuantity , String>(
            url = "https://ecommerce-ktor-4641e7ff1b63.herokuapp.com/cart/${sharedPreferences.getInt("id" , 0)}/${updatedProduct.id}",
            method = HttpMethod.Put,
            mapper = { updateQuantity ->
                updateQuantity.msg
            },
            body = updatedProduct
        )
    }

    override suspend fun decrementQuantity(updatedProduct: UpdatedProduct): ResultWrapper<String> {
        return makeWebRequest<UpdateQuantity , String>(
            url = "https://ecommerce-ktor-4641e7ff1b63.herokuapp.com/cart/${sharedPreferences.getInt("id" , 0)}/${updatedProduct.id}",
            method = HttpMethod.Put,
            mapper = { updateQuantity ->
                updateQuantity.msg
            },
            body = updatedProduct
        )
    }

    override suspend fun makeOrder(addressDataDomain: AddressDataDomain): ResultWrapper<Long> {
        return makeWebRequest<MakeOrderResponseModel , Long>(
            url = "https://ecommerce-ktor-4641e7ff1b63.herokuapp.com/v2/orders/${sharedPreferences.getInt("id" , 0)}",
            method = HttpMethod.Post,
            body = AddressDataData.fromAddressDataDomain(addressDataDomain),
            mapper = { _ ->
                1
            }
        )
    }

    override suspend fun getOrders(): ResultWrapper<List<Order>> {
        return makeWebRequest<GetOrdersResponseModel , List<Order>>(
            url = "https://ecommerce-ktor-4641e7ff1b63.herokuapp.com/v2/orders/${sharedPreferences.getInt("id" , 0)}",
            method = HttpMethod.Get,
            mapper = { getOrdersResponseModel->
                getOrdersResponseModel.data
            }
        )
    }

    override suspend fun deleteOrder(orderID: Int): ResultWrapper<String> {
        return makeWebRequest<String , String>(
            url = "https://ecommerce-ktor-4641e7ff1b63.herokuapp.com/v2/orders/$orderID",
            method = HttpMethod.Delete
        )
    }

    override suspend fun login(email: String, password: String): ResultWrapper<UserResponse> {
        return makeWebRequest<UserResponseModel , UserResponse>(
            url = "https://ecommerce-ktor-4641e7ff1b63.herokuapp.com/login",
            method = HttpMethod.Post,
            body = LoginRequestModel(email , password),
            mapper = { userResponseModel ->
                userResponseModel.data
            }
        )
    }

    override suspend fun register(
        email: String,
        password: String,
        name: String
    ): ResultWrapper<UserResponse> {
        return makeWebRequest<UserResponseModel , UserResponse>(
            url = "https://ecommerce-ktor-4641e7ff1b63.herokuapp.com/signup",
            method = HttpMethod.Post,
            body = RegisterRequestModel(email , name , password),
            mapper = { userResponseModel ->
                userResponseModel.data
            }
        )
    }

    private suspend inline fun <reified T , reified R> makeWebRequest(
        url: String,
        method: HttpMethod,
        body: Any? = null,
        headers: Map<String , String> = emptyMap(),
        parameters: Map<String , String> = emptyMap(),
        noinline mapper: ((T) -> R)? = null
        ): ResultWrapper<R>{
        return try {
            val response = client.request(url) {
                this.method = method

                url{
                    this.parameters.appendAll(Parameters.build {
                        parameters.forEach{ (key , value) ->
                            append(key , value)
                        }
                    })
                }

                headers.forEach{ (key , value) ->
                    header(key , value)
                }

                if(body != null){
                    setBody(body)
                }

                contentType(ContentType.Application.Json)

            }.body<T>()

            val result = mapper?.let { it(response) } ?: response as R

            ResultWrapper.Success(result)

        } catch (e: ClientRequestException){
            Log.d("mmm" , "ClientRequestException")
            ResultWrapper.Failure(e)
        } catch (e: ServerResponseException){
            Log.d("mmm" , "ServerResponseException")
            ResultWrapper.Failure(e)
        } catch (e: IOException){
            Log.d("mmm" , "IOException")
            ResultWrapper.Failure(e)
        } catch (e: Exception){
            Log.d("mmm" , "Exception")
            Log.d("mmm" , e.message.toString())
            ResultWrapper.Failure(e)
        }
    }
}