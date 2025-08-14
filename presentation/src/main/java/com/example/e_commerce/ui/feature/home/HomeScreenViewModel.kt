package com.example.e_commerce.ui.feature.home

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Category
import com.example.domain.model.Product
import com.example.domain.network.ResultWrapper
import com.example.domain.use_cases.GetCategoryUseCase
import com.example.domain.use_cases.GetProductUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeScreenViewModel(
    private val getProductUseCase: GetProductUseCase,
    private val getCategoryUseCase: GetCategoryUseCase
): ViewModel() , KoinComponent{

    private val homeScreenViewModelState = MutableStateFlow<HomeScreenUIEvents>(HomeScreenUIEvents.Loading)
    val homeScreenUIState = homeScreenViewModelState.asStateFlow()

    private val context: Context by inject()
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("user" , Context.MODE_PRIVATE)

    init {
        getAllShop()
    }

    private suspend fun getCategories(): List<Category>{
        getCategoryUseCase.getCategories().let { result ->
            when(result){
                is ResultWrapper.Success -> {
                    return result.value
                }

                is ResultWrapper.Failure -> {
                    return emptyList()
                }
            }

        }
    }

    private fun getAllShop(){
        viewModelScope.launch {
            homeScreenViewModelState.value = HomeScreenUIEvents.Loading

            val feature = getProducts(1)
            val mostPopular = getProducts(2)
            val categories = getCategories()

            if(feature.isEmpty() && categories.isEmpty() && mostPopular.isEmpty()){
                homeScreenViewModelState.value = HomeScreenUIEvents.Error("Something error in the service")
            }

            homeScreenViewModelState.value = HomeScreenUIEvents.Success(feature , mostPopular , categories)
        }
    }

    private suspend fun getProducts(category: Int): List<Product>{
        getProductUseCase.getProduct(category).let { resultWrapper ->
            when(resultWrapper){
                is ResultWrapper.Success -> {
                    return resultWrapper.value
                }
                is ResultWrapper.Failure -> {
                    return emptyList()
                }
            }
        }
    }
}

sealed class HomeScreenUIEvents{
    data object Loading: HomeScreenUIEvents()
    data class Success(
        val feature: List<Product>,
        val mostPopular: List<Product>,
        val categories: List<Category>
    ) : HomeScreenUIEvents()
    data class Error(val message: String?) : HomeScreenUIEvents()
}