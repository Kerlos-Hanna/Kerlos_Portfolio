package com.example.e_commerce.ui.feature.check_out

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.network.ResultWrapper
import com.example.domain.request.order.AddressDataDomain
import com.example.domain.response.get_product_cart.ProductCartItem
import com.example.domain.use_cases.GetProductCartUseCase
import com.example.domain.use_cases.MakeOrderUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CheckOutScreenViewModel(
    private val getProductCartUseCase: GetProductCartUseCase,
    private val makeOrderUseCase: MakeOrderUseCase,
): ViewModel() , KoinComponent{
    private val viewModel = MutableStateFlow<CheckOutScreenState>(CheckOutScreenState.Loading)
    val viewModelUI = viewModel.asStateFlow()

    private val context: Context by inject()
    private val sharedPreferences = context.getSharedPreferences("user" , Context.MODE_PRIVATE)

    init {
        viewModelScope.launch {
            getProductCart()
        }
    }

     suspend fun getProductCart(){
        getProductCartUseCase.getProductCart(sharedPreferences.getInt("id" , 0)).let { resultWrapper ->
            when(resultWrapper){
                is ResultWrapper.Failure -> {
                    viewModel.value = CheckOutScreenState.Failure(resultWrapper.exception.message.toString())
                }

                is ResultWrapper.Success -> {
                    viewModel.value = CheckOutScreenState.Success(resultWrapper.value)
                }
            }
        }
    }

    fun calculateTotalPrice(list: List<ProductCartItem>): Float{
        var totalPrice = 0f

        list.forEach { productCartItem ->
            totalPrice += productCartItem.price * productCartItem.quantity
        }

        return totalPrice
    }

    fun makeOrder(addressDataDomain: AddressDataDomain){
        viewModel.value = CheckOutScreenState.Loading
        viewModelScope.launch {

            Log.d("mmm" , "make order")

            makeOrderUseCase.makeOrder(addressDataDomain).let { resultWrapper ->
                when(resultWrapper){
                    is ResultWrapper.Failure -> {
                        viewModel.value = CheckOutScreenState.Failure(resultWrapper.exception.message.toString())
                    }
                    is ResultWrapper.Success -> {
                        viewModel.value = CheckOutScreenState.MakeOrderDone(resultWrapper.value)
                    }
                }
            }
        }
    }
}

sealed class CheckOutScreenState{
    data object Loading: CheckOutScreenState()
    data class MakeOrderDone(val orderID: Long): CheckOutScreenState()
    data class Success(val data: List<ProductCartItem>): CheckOutScreenState()
    data class Failure(val exception: String): CheckOutScreenState()
}