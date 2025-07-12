package com.example.chatter.feature.auth.signin

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(): ViewModel() {

    private val state = MutableStateFlow<SignInState>(SignInState.Nothing)
    val stateUI = state.asStateFlow()

    fun signIn(email: String, password: String){
        state.value = SignInState.Loading

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email , password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful) state.value = SignInState.Success

                else state.value = SignInState.Error
            }
    }
}

sealed class SignInState{
    data object Nothing: SignInState()
    data object Loading: SignInState()
    data object Success: SignInState()
    data object Error: SignInState()
}