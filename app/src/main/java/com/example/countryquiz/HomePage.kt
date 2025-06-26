package com.example.countryquiz

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomePage(navController: NavController) {
    val focusManager = LocalFocusManager.current
    var isTextFieldFocused by remember { mutableStateOf(false) }

    BackHandler(enabled = isTextFieldFocused) {
        focusManager.clearFocus()
    }

    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { padding ->
        Box(
            modifier = Modifier.fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFFB567E5), Color(0xFF7b0daf))
                    )
                )
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }) {
                    focusManager.clearFocus()
                }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(text = "Quiz App", fontSize = 30.sp, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(0.35f)
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(30.dp))
                        .background(Color.White)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Welcome",
                            fontSize = 35.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(10.dp)
                        )

                        Text(
                            text = "Please enter your name",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Light,
                            modifier = Modifier.padding(10.dp)
                        )

                        var textFieldValue by remember { mutableStateOf("") }

                        OutlinedTextField(
                            value = textFieldValue,
                            onValueChange = {
                                textFieldValue = it
                            },
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color.White,
                                focusedContainerColor = Color.White,
                                focusedLabelColor = Color.Black,
                                focusedIndicatorColor = Color.Black,
                                cursorColor = Color.Black
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .onFocusChanged { focusState ->
                                    isTextFieldFocused = focusState.isFocused
                                },
                            label = {
                                Text(text = "Name")
                            }
                        )

                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.5f)
                                .padding(top = 8.dp),
                            shape = RoundedCornerShape(10.dp),
                            onClick = {
                                if (textFieldValue.isNotEmpty()) {
                                    navController.navigate(route = "Quiz/$textFieldValue"){
                                        popUpTo(0){
                                            inclusive = true
                                        }
                                    }
                                } else {
                                    val job = scope.launch {
                                        launch {
                                            snackBarHostState.showSnackbar(
                                                message = "You must enter a name",
                                                duration = SnackbarDuration.Indefinite
                                            )
                                        }

                                        delay(2000)

                                        snackBarHostState.currentSnackbarData?.dismiss()
                                    }

                                    if(job.isCompleted)
                                        job.cancel()
                                }
                            }
                        ) {
                            Text(text = "START")
                        }
                    }
                }
            }
        }
    }
}