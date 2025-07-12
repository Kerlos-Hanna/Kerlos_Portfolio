package com.example.chatter.feature.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {

    val viewModel: HomeViewModel = hiltViewModel()
    val stateChannelUI = viewModel.stateChannelUI.collectAsState()
    val stateOfLoading = viewModel.stateOfLoadingUI.collectAsState()
    var isBottomSheetOpened by remember { mutableStateOf(false) }
    var addChannelName by remember { mutableStateOf("") }
    var search by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val isFocused = remember { mutableStateOf(false) }

    BackHandler(enabled = isFocused.value) {
        focusManager.clearFocus()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
            },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                isBottomSheetOpened = true
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Chat"
                )
            }
        },

        bottomBar = {
            if(isBottomSheetOpened){
                ModalBottomSheet(
                    onDismissRequest = {
                        isBottomSheetOpened = false
                    }
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(text = "Add Chat")

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = addChannelName,
                            onValueChange = {
                                addChannelName = it
                            },
                            label = {
                                Text(text = "Chat Name")
                            }
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Button(
                            onClick = {
                                isBottomSheetOpened = false
                                viewModel.addChannel(addChannelName)
                                addChannelName = ""
                            },
                            enabled = addChannelName.isNotEmpty(),
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 10.dp)
                        ) {
                            Text("Add")
                        }
                    }
                }
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier.fillMaxSize().background(Color.Black).padding(paddingValues).padding(16.dp)
        ) {
            Text(
                text = "Chats",
                fontSize = 30.sp,
                color = Color.White,
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = search,
                onValueChange = {
                    search = it
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF626262),
                    unfocusedContainerColor = Color(0xFF626262),
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Black,
                    unfocusedIndicatorColor = Color.Black
                ),
                placeholder = {
                    Text(text = "Search..." , color = Color.White)
                },
                trailingIcon = {
                    Image(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon"
                    )
                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
                    .onFocusChanged { focusState ->
                        isFocused.value = focusState.isFocused
                    }
            )

            Spacer(modifier = Modifier.height(10.dp))

            if(stateOfLoading.value){
                Column(
                    modifier = Modifier.background(Color.Black).fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            else{
                LazyColumn(modifier = Modifier.fillMaxSize().background(Color.Black)) {
                    items(stateChannelUI.value) { channel ->
                        Button(
                            modifier = Modifier
                                .fillMaxWidth(),
                            onClick = {
                                navController.navigate("ChatScreen/${channel.name}/${channel.id}")
                            },

                            shape = RoundedCornerShape(15.dp),

                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF626262),
                                contentColor = Color.White
                            )
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier.size(50.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFF498a24)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = "${channel.name[0]}" , fontSize = 25.sp)
                                }

                                Text(text = channel.name , modifier = Modifier.padding(start = 10.dp) , fontSize = 25.sp)
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }
    }
}