package com.example.e_commerce.ui.feature.cart

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.network.ResultWrapper
import com.example.domain.request.add_product.DataItem
import com.example.domain.response.get_product_cart.ProductCartItem
import com.example.domain.response.update_quantity.UpdatedProduct
import com.example.domain.use_cases.DecrementQuantityUseCase
import com.example.domain.use_cases.DeleteProductCartUseCase
import com.example.domain.use_cases.GetProductCartUseCase
import com.example.domain.use_cases.IncrementQuantityUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CartScreenViewModel(
    private val useCase: GetProductCartUseCase,
    private val useCaseDelete: DeleteProductCartUseCase,
    private val useCaseIncrementQuantity: IncrementQuantityUseCase,
    private val useCaseDecrementQuantity: DecrementQuantityUseCase
): ViewModel(),KoinComponent {
    private val viewModel = MutableStateFlow<CartScreenState>(CartScreenState.Loading)
    val viewModelUI = viewModel.asStateFlow()

    private val context: Context by inject()
    private val sharedPreferences = context.getSharedPreferences("user" , Context.MODE_PRIVATE)

    init {
        viewModelScope.launch {
            getProductCart()
        }
    }

    suspend fun getProductCart(){
        useCase.getProductCart(sharedPreferences.getInt("id" , 0)).let { resultWrapper ->
            when(resultWrapper){
                is ResultWrapper.Failure -> {
                    viewModel.value = CartScreenState.Failure(resultWrapper.exception.message.toString())
                }

                is ResultWrapper.Success -> {
                    viewModel.value = CartScreenState.Success(resultWrapper.value)
                }
            }
        }
    }

    suspend fun deleteProductCart(productID: Int) {
        viewModel.value = CartScreenState.Loading
        useCaseDelete.deleteProductCartUseCase(productID).let { resultWrapper ->
            when (resultWrapper) {
                is ResultWrapper.Failure -> {
                    viewModel.value = CartScreenState.Failure(resultWrapper.exception.message.toString())
                }

                is ResultWrapper.Success -> {
                    getProductCart()
                }
            }
        }
    }

     fun decrementQuantity(dataItem: DataItem){
        viewModelScope.launch {
            viewModel.value = CartScreenState.Loading
            useCaseDecrementQuantity.decrementQuantity(UpdatedProduct.fromDataItem(dataItem)).let { resultWrapper ->
                when(resultWrapper){
                    is ResultWrapper.Failure -> {
                        viewModel.value = CartScreenState.Failure(resultWrapper.exception.message.toString())
                    }
                    is ResultWrapper.Success -> {
                        getProductCart()
                    }
                }
            }
        }
    }

     fun incrementQuantity(dataItem: DataItem){
         viewModelScope.launch {
             viewModel.value = CartScreenState.Loading
             useCaseIncrementQuantity.incrementQuantity(UpdatedProduct.fromDataItem(dataItem)).let { resultWrapper ->
                 when(resultWrapper){
                     is ResultWrapper.Failure -> {
                         viewModel.value = CartScreenState.Failure(resultWrapper.exception.message.toString())
                     }
                     is ResultWrapper.Success -> {
                         getProductCart()
                     }
                 }
             }
         }
    }
}

sealed class CartScreenState{
    data object Loading: CartScreenState()
    data class Success(val data: List<ProductCartItem>): CartScreenState()
    data class Failure(val exception: String): CartScreenState()
}