package com.example.e_commerce.ui.feature.address_data

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.e_commerce.navigation.CheckOutScreen
import com.example.e_commerce.ui.model.AddressData


const val USER_ADDRESS_SCREEN = "user_address_screen"

@Composable
fun AddressScreen(navController: NavController , paddingValues: PaddingValues) {

    val addressLine = remember { mutableStateOf("") }
    val government = remember { mutableStateOf("") }
    val postalCode = remember { mutableStateOf("") }
    val country = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTextField("Address Line" , addressLine)
        CustomTextField("Government" , government)
        CustomTextField("Postal Code" , postalCode)
        CustomTextField("Country" , country)

        Box(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                navController.previousBackStackEntry?.savedStateHandle?.set(USER_ADDRESS_SCREEN , AddressData(
                    addressLine.value,
                    government.value,
                    postalCode.value,
                    country.value
                ))

                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 10.dp),
            enabled = addressLine.value.isNotEmpty() && government.value.isNotEmpty() &&
                    postalCode.value.isNotEmpty() && country.value.isNotEmpty()
        ) {
            Text(text = "Save")
        }
    }
}

@Composable
fun CustomTextField(label: String , data: MutableState<String>) {

    OutlinedTextField(
        value = data.value,
        onValueChange = {
            data.value = it
        },
        label = {
            Text(text = label)
        },
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
}