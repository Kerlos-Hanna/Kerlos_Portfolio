package com.example.chatter.feature.auth.signup

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chatter.R
import com.example.chatter.feature.auth.customs.CustomTextFieldSignUp

@Composable
fun SignUpScreen(navController: NavController) {
    val focusManager = LocalFocusManager.current
    val isFocused = remember { mutableStateOf(false) }
    val isTextField1NotEmpty = remember { mutableStateOf(false) }
    val isTextField2NotEmpty = remember { mutableStateOf(false) }
    val isTextField3NotEmpty = remember { mutableStateOf(false) }
    val isTextField4NotEmpty = remember { mutableStateOf(false) }
    var isSignUpStateError by remember { mutableStateOf(false) }
    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }
    val viewModel:SignUpViewModel = hiltViewModel()
    val stateUI = viewModel.stateUI.collectAsState()

    BackHandler(enabled = isFocused.value) {
        focusManager.clearFocus()
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
                .imePadding()
                .padding(horizontal = 16.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    focusManager.clearFocus()
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "Chatter Logo"
            )

            CustomTextFieldSignUp(
                "Full Name",
                isFocused,
                isTextField1NotEmpty,
                password,
                confirmPassword,
                name,
                email
            )
            CustomTextFieldSignUp(
                "Email",
                isFocused,
                isTextField2NotEmpty,
                password,
                confirmPassword,
                name,
                email
            )
            CustomTextFieldSignUp(
                "Password",
                isFocused,
                isTextField3NotEmpty,
                password,
                confirmPassword,
                name,
                email
            )
            CustomTextFieldSignUp(
                "Confirm Password",
                isFocused,
                isTextField4NotEmpty,
                password,
                confirmPassword,
                name,
                email
            )

            LaunchedEffect(stateUI.value) {
                if(stateUI.value == SignUpState.Success){
                    navController.navigate("Home Screen"){
                        popUpTo(0){
                            inclusive = true
                        }
                    }
                }

                else if(stateUI.value == SignUpState.Error){
                    isSignUpStateError = true
                }
            }

            if(isSignUpStateError){
                if(password.value.length < 6 && !email.value.contains("@gmail.com")){
                    AlertDialog(
                        title = {
                            Text(text = "Warning")
                        },
                        text = {
                            Column {
                                Text(text = "Please your password should be at least 6 characters!")
                                Text(text = "Please your email should end with (@gmail.com)!")
                            }
                        },
                        onDismissRequest = {
                            isSignUpStateError = false
                        },
                        confirmButton = {
                            TextButton(onClick = {isSignUpStateError = false}){
                                Text(text = "Ok")
                            }
                        }
                    )
                }

                else if(password.value.length < 6){
                    AlertDialog(
                        title = {
                            Text(text = "Warning")
                        },
                        text = {
                            Column {
                                Text(text = "Please your password should be at least 6 characters!")
                            }
                        },
                        onDismissRequest = {
                            isSignUpStateError = false
                        },
                        confirmButton = {
                            TextButton(onClick = {isSignUpStateError = false}){
                                Text(text = "Ok")
                            }
                        }
                    )
                }

                else{
                    AlertDialog(
                        title = {
                            Text(text = "Warning")
                        },
                        text = {
                            Column {
                                Text(text = "Please your email should end with (@gmail.com)!")
                            }
                        },
                        onDismissRequest = {
                            isSignUpStateError = false
                        },
                        confirmButton = {
                            TextButton(onClick = {isSignUpStateError = false}){
                                Text(text = "Ok")
                            }
                        }
                    )
                }
            }

            if(stateUI.value == SignUpState.Loading){
                CircularProgressIndicator()
            }

            Button(
                onClick = {
                    viewModel.signUp(name.value , email.value , password.value)
                },

                enabled = isTextField1NotEmpty.value && isTextField2NotEmpty.value
                        && isTextField3NotEmpty.value && isTextField4NotEmpty.value && password.value==confirmPassword.value,

                modifier = Modifier.fillMaxWidth()
                    .padding(top = 5.dp)
            ) {
                Text(text = "Sign Up")
            }

            TextButton(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Text(text = "Already have an account? Sign In" , color = Color(0xFF6750A4))
            }
        }
    }
}