package com.example.e_commerce.ui.feature.account.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.network.ResultWrapper
import com.example.domain.use_cases.LoginUseCase
import com.example.e_commerce.ui.feature.account.save_user_data.SaveRestoreUserData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginScreenViewModel(private val loginUseCase: LoginUseCase): ViewModel() {

    private val viewModel = MutableStateFlow<LoginScreenState>(LoginScreenState.Nothing)
    val viewModelUI = viewModel.asStateFlow()

    fun login(email: String , password: String){
        viewModelScope.launch {
            viewModel.value = LoginScreenState.Loading
            loginUseCase.login(email , password).let { resultWrapper ->
                when(resultWrapper){
                    is ResultWrapper.Failure -> {
                        viewModel.value = LoginScreenState.Failure

                        delay(50)

                        viewModel.value = LoginScreenState.Nothing
                    }
                    is ResultWrapper.Success -> {
                        SaveRestoreUserData.storeUser(resultWrapper.value)
                        viewModel.value = LoginScreenState.Success
                    }
                }
            }
        }
    }
}

sealed class LoginScreenState{
    data object Nothing: LoginScreenState()
    data object Loading: LoginScreenState()
    data object Success : LoginScreenState()
    data object Failure : LoginScreenState()
}