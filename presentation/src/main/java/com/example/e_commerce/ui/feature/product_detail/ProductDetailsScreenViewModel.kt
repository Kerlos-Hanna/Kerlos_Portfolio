package com.example.e_commerce.ui.feature.product_detail

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.network.ResultWrapper
import com.example.domain.request.add_product.AddCartRequestModel
import com.example.domain.use_cases.AddProductToCartUseCase
import com.example.e_commerce.ui.model.UIProduct
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ProductDetailsScreenViewModel(
    private val productToCartUseCase: AddProductToCartUseCase
): ViewModel() , KoinComponent{
    private val viewModel = MutableStateFlow<DetailsScreenState>(DetailsScreenState.Nothing)
    val viewModelUI = viewModel.asStateFlow()

    private val context: Context by inject()
    private val sharedPreferences = context.getSharedPreferences("user" , Context.MODE_PRIVATE)

    fun addProductToCart(product: UIProduct){
        viewModelScope.launch {
            viewModel.value = DetailsScreenState.Loading
            productToCartUseCase.addProductToCart(
                AddCartRequestModel(
                    productId = product.id,
                    userId = sharedPreferences.getInt("id" , 0),
                    quantity = 1,
                    productName = product.title,
                    price = product.price.toInt(),
                    imageUrl = product.image
                )
            ).let { resultWrapper ->
                when(resultWrapper){
                    is ResultWrapper.Failure -> {
                        Log.d("mmm" , resultWrapper.exception.message.toString())
                        viewModel.value = DetailsScreenState.Error(resultWrapper.exception.message.toString())
                    }

                    is ResultWrapper.Success -> {
                        viewModel.value = DetailsScreenState.Success(resultWrapper.value)
                    }
                }
            }
        }
    }
}

sealed class DetailsScreenState{
    data object Nothing: DetailsScreenState()
    data object Loading: DetailsScreenState()
    data class Success(val message: String): DetailsScreenState()
    data class Error(val message: String): DetailsScreenState()
}