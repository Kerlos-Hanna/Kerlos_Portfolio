package com.example.chatter.feature.auth.customs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun CustomTextFieldSignIn(
    label: String,
    isFocused: MutableState<Boolean>,
    isTextFieldFieldNotEmpty: MutableState<Boolean>,
    email: MutableState<String>,
    password: MutableState<String>
) {
    var inputText by remember { mutableStateOf("") }

    isTextFieldFieldNotEmpty.value = inputText.isNotEmpty()

    OutlinedTextField(
        value = inputText ,

        onValueChange = {
            inputText = it

            if(label == "Email") email.value = it
            if(label == "Password") password.value = it
        },

        label = {
            Text(text = label , fontWeight = FontWeight.Light)
        },

        visualTransformation = if(label == "Password" || label == "Confirm Password") PasswordVisualTransformation()
        else VisualTransformation.None,

        modifier = Modifier.fillMaxWidth()
            .onFocusChanged { focusState ->
                isFocused.value = focusState.isFocused
            }
    )
}

@Composable
fun CustomTextFieldSignUp(
    label: String,
    isFocused: MutableState<Boolean>,
    isTextFieldFieldNotEmpty: MutableState<Boolean>,
    password: MutableState<String>,
    confirmPassword: MutableState<String>,
    name: MutableState<String>,
    email: MutableState<String>
) {
    var inputText by remember { mutableStateOf("") }

    isTextFieldFieldNotEmpty.value = inputText.isNotEmpty()

    OutlinedTextField(
        value = inputText ,

        onValueChange = {
            inputText = it

            if(label=="Password") password.value = it
            if(label=="Full Name") name.value = it
            if(label=="Email") email.value = it
            if(label=="Confirm Password") confirmPassword.value = it
        },

        label = {
            Text(text = label , fontWeight = FontWeight.Light)
        },

        visualTransformation = if(label == "Password" || label == "Confirm Password") PasswordVisualTransformation()
        else VisualTransformation.None,

        isError = label=="Confirm Password" && password.value.isNotEmpty() &&
                inputText.isNotEmpty() && password.value != inputText,

        modifier = Modifier.fillMaxWidth()
            .onFocusChanged { focusState ->
                isFocused.value = focusState.isFocused
            }
    )
}