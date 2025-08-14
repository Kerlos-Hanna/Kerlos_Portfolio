package com.example.e_commerce.ui.feature.account.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.network.ResultWrapper
import com.example.domain.use_cases.RegisterUseCase
import com.example.e_commerce.ui.feature.account.save_user_data.SaveRestoreUserData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterScreenViewModel(private val registerUseCase: RegisterUseCase): ViewModel() {

    private val viewModel = MutableStateFlow<RegisterScreenState>(RegisterScreenState.Nothing)
    val viewModelUI = viewModel.asStateFlow()

    fun register(email: String , password: String , name: String){
        viewModelScope.launch {
            viewModel.value = RegisterScreenState.Loading
            registerUseCase.register(email , password , name).let { resultWrapper ->
                when(resultWrapper){
                    is ResultWrapper.Failure -> {
                        viewModel.value = RegisterScreenState.Failure

                        delay(50)

                        viewModel.value = RegisterScreenState.Nothing
                    }
                    is ResultWrapper.Success -> {
                        Log.d("mmm" , resultWrapper.value.toString())
                        SaveRestoreUserData.storeUser(resultWrapper.value)
                        viewModel.value = RegisterScreenState.Success
                    }
                }
            }
        }
    }
}

sealed class RegisterScreenState{
    data object Nothing: RegisterScreenState()
    data object Loading: RegisterScreenState()
    data object Success : RegisterScreenState()
    data object Failure : RegisterScreenState()
}