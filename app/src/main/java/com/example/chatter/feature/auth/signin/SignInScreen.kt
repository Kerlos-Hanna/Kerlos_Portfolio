package com.example.chatter.feature.auth.signin

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chatter.R
import com.example.chatter.feature.auth.customs.CustomTextFieldSignIn

@Composable
fun SignInScreen(navController: NavController) {

    val focusManager = LocalFocusManager.current
    val isFocused = remember { mutableStateOf(false) }
    val isTextField1NotEmpty = remember { mutableStateOf(false) }
    val isTextField2NotEmpty = remember { mutableStateOf(false) }
    var stateAuthError by remember { mutableStateOf(false) }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val viewModel: SignInViewModel = hiltViewModel()
    val stateUI = viewModel.stateUI.collectAsState()

    BackHandler(enabled = isFocused.value) {
        focusManager.clearFocus()
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
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

            CustomTextFieldSignIn("Email" , isFocused , isTextField1NotEmpty , email , password)
            CustomTextFieldSignIn("Password" , isFocused , isTextField2NotEmpty , email , password)

            if(stateUI.value == SignInState.Loading){
                CircularProgressIndicator()
            }

            LaunchedEffect(stateUI.value) {
                if(stateUI.value == SignInState.Success){
                    navController.navigate("Home Screen"){
                        popUpTo(0){
                            inclusive = true
                        }
                    }
                }

                else if(stateUI.value==SignInState.Error){
                    stateAuthError = true
                }
            }

            if(stateAuthError){
                AlertDialog(
                    title = {
                        Text(text = "Warning")
                    },
                    text = {
                        Text(text = "Please check your email or password well!")
                    },
                    onDismissRequest = {
                        stateAuthError = false
                    },
                    confirmButton = {
                        TextButton(onClick = {stateAuthError = false}){
                            Text(text = "Ok")
                        }
                    }
                )
            }

            Button(
                onClick = {
                    viewModel.signIn(email.value , password.value)
                },

                enabled = isTextField1NotEmpty.value && isTextField2NotEmpty.value &&
                        (stateUI.value == SignInState.Nothing || stateUI.value == SignInState.Error),

                modifier = Modifier.fillMaxWidth()
                    .padding(top = 5.dp)
            ) {
                Text(text = "Sign In")
            }

            TextButton(
                onClick = {
                    navController.navigate("SignUpScreen")
                }
            ) {
                Text(text = "Don't have an account? Sign Up" , color = Color(0xFF6750A4))
            }
        }
    }
}