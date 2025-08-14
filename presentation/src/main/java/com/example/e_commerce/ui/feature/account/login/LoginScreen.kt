package com.example.e_commerce.ui.feature.account.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.e_commerce.R
import com.example.e_commerce.navigation.HomeScreen
import com.example.e_commerce.navigation.RegisterScreen
import com.example.e_commerce.ui.theme.PurpleBottomSheet
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    viewModel: LoginScreenViewModel = koinViewModel()
) {

    val viewModelUI = viewModel.viewModelUI.collectAsState()

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(paddingValues)
            .padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when(viewModelUI.value){
            is LoginScreenState.Failure -> {
                Toast.makeText(LocalContext.current , "Error, try again!" , Toast.LENGTH_LONG).show()
            }

            is LoginScreenState.Loading -> {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is LoginScreenState.Nothing -> {
                CustomOutlinedTextField(stringResource(R.string.email) , email)
                CustomOutlinedTextField(stringResource(R.string.password) , password)

                Box(modifier = Modifier.weight(1f))
            }

            is LoginScreenState.Success -> {
                LaunchedEffect(true) {
                    navController.navigate(HomeScreen){
                        popUpTo(0){
                            inclusive = true
                        }
                    }
                }
            }
        }

        Button(
            onClick = {
                viewModel.login(email.value , password.value)
            },
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp),
            enabled = email.value.isNotEmpty() && password.value.isNotEmpty()
        ) {
            Text(text = stringResource(R.string.sign_in))
        }

        TextButton(
            onClick = {
                navController.navigate(RegisterScreen)
            }
        ) {
            Text(text = stringResource(R.string.do_not_have_an_account) , color = PurpleBottomSheet)
        }
    }
}

@Composable
fun CustomOutlinedTextField(label: String , inputTextField: MutableState<String>) {

    OutlinedTextField(
        value = inputTextField.value,
        onValueChange = {
            inputTextField.value = it
        },
        label = {
            Text(text = label)
        },
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 16.dp),
        visualTransformation = if(label == stringResource(R.string.password))
            PasswordVisualTransformation()
        else
            VisualTransformation.None
    )
}