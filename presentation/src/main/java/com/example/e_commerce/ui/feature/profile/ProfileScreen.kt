package com.example.e_commerce.ui.feature.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(paddingValues: PaddingValues ,viewModel: ProfileScreenViewModel = koinViewModel()) {
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
    ) {
        Text(text = "ID: " + viewModel.sharedPreferences.getInt("id" , 0).toString())

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Name: " + viewModel.sharedPreferences.getString("name" , null).toString())

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Email: " + viewModel.sharedPreferences.getString("email" , null).toString())

        Spacer(modifier = Modifier.height(20.dp))
    }
}