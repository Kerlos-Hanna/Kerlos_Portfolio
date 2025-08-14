package com.example.e_commerce.ui.feature.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.network.ResultWrapper
import com.example.domain.response.get_orders.Order
import com.example.domain.use_cases.DeleteOrderUseCase
import com.example.domain.use_cases.GetOrdersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OrdersScreenViewModel(
    private val getOrdersUseCase: GetOrdersUseCase,
    private val deleteOrdersUseCase: DeleteOrderUseCase
): ViewModel(){
    private val viewModel = MutableStateFlow<OrdersState>(OrdersState.Loading)
    val viewModelUIState = viewModel.asStateFlow()

    init {
        getOrders()
    }

    private fun getOrders(){
        viewModelScope.launch {
            viewModel.value = OrdersState.Loading
            getOrdersUseCase.getOrders().let { resultWrapper ->
                when(resultWrapper){
                    is ResultWrapper.Failure -> {
                        viewModel.value = OrdersState.Failure("error")
                    }

                    is ResultWrapper.Success -> {
                        viewModel.value = OrdersState.Success(resultWrapper.value)
                    }
                }
            }
        }
    }

    fun deleteOrder(orderID: Int){
        viewModelScope.launch {
            viewModel.value = OrdersState.Loading
            deleteOrdersUseCase.deleteOrder(orderID).let { resultWrapper ->
                when(resultWrapper){
                    is ResultWrapper.Failure -> {
                        viewModel.value = OrdersState.Failure(resultWrapper.exception.message.toString())
                    }

                    is ResultWrapper.Success -> {
                        getOrders()
                    }
                }
            }
        }
    }
}

sealed class OrdersState{
    data class Failure(val exception: String): OrdersState()
    data class Success(val orders: List<Order>): OrdersState()
    data object Loading: OrdersState()
}