package com.example.chatter.feature.auth.signup

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(): ViewModel(){
    private val state = MutableStateFlow<SignUpState>(SignUpState.Nothing)
    val stateUI = state.asStateFlow()

    fun signUp(name: String , email:String , password: String){
        state.value = SignUpState.Loading

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email , password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    task.result.user.let {
                        it?.updateProfile(
                            UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .build()
                        )
                    }

                    val userData = mapOf(
                        "name" to name,
                        "email" to email
                    )

                    storeUserDataInDatabase(task.result.user?.uid!!, userData)

                    state.value = SignUpState.Success
                }

                else{
                    state.value = SignUpState.Error
                }
            }
    }

    private fun storeUserDataInDatabase(uid: String , userData: Map<String , String>){
        val usersRef = FirebaseDatabase.getInstance().getReference("Users")

        usersRef.child(uid).setValue(userData)
    }
}

sealed class SignUpState{
    data object Nothing: SignUpState()
    data object Loading: SignUpState()
    data object Success: SignUpState()
    data object Error: SignUpState()
}