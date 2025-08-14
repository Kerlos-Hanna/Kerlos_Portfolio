package com.example.e_commerce.ui.feature.orders

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.domain.response.get_orders.Order
import com.example.e_commerce.ui.theme.MyTypography
import org.koin.androidx.compose.koinViewModel

@Composable
fun OrdersScreen(paddingValues: PaddingValues , viewModel: OrdersScreenViewModel = koinViewModel()) {

    val viewModelUI = viewModel.viewModelUIState.collectAsState()
    var state by remember { mutableIntStateOf(0) }
    var listOrders by remember { mutableStateOf<List<Order>>(emptyList()) }

    state = when(viewModelUI.value){
        is OrdersState.Failure -> {
            3
        }

        is OrdersState.Loading -> {
            0
        }

        is OrdersState.Success -> {
            listOrders = (viewModelUI.value as OrdersState.Success).orders
            1
        }
    }

    if(state == 0){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()

            Text(text = "Loading...")
        }
    }

    else if(state == 1){
        if(listOrders.isNotEmpty()){
            LazyColumn(
                modifier = Modifier.fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 10.dp)
            ) {
                items(listOrders){ order ->
                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .clip(RoundedCornerShape(15.dp))
                            .background(Color.LightGray)
                            .padding(10.dp)
                    ) {
                        IconButton(
                            modifier = Modifier.align(Alignment.TopEnd),
                            onClick ={
                                viewModel.deleteOrder(orderID = order.id)
                            }
                        ) {
                            Icon(
                                tint = Color.Red,
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete Order",
                            )
                        }

                        Column {
                            Text(
                                text = "Order ID: " + order.id.toString(),
                                style = MyTypography.headlineSmall
                            )

                            Text(
                                text = "Order Date: " + order.orderDate,
                                style = MyTypography.headlineSmall
                            )

                            Text(
                                text = "Total amount: " + order.totalAmount.toString() + "$",
                                style = MyTypography.headlineSmall
                            )

                            Text(
                                text = "Status: " + order.status,
                                style = MyTypography.headlineSmall
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(30.dp))
                }
            }
        }

        else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "No orders available!")
            }
        }
    }

    else{
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Error")
        }
    }
}