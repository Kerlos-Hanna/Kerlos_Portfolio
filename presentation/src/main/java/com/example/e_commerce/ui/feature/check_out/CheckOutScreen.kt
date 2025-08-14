package com.example.e_commerce.ui.feature.check_out

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.domain.response.get_product_cart.ProductCartItem
import com.example.e_commerce.R
import com.example.e_commerce.navigation.AddressScreen
import com.example.e_commerce.ui.feature.address_data.USER_ADDRESS_SCREEN
import com.example.e_commerce.ui.model.AddressData
import com.example.e_commerce.ui.theme.MyTypography
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.text.DecimalFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckOutScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    viewModel: CheckOutScreenViewModel = koinViewModel(),
) {

    var addressData: AddressData by remember { mutableStateOf(AddressData(
        "",
        "",
        "",
        ""
    )) }

    LaunchedEffect(navController) {
        navController.currentBackStackEntry?.savedStateHandle?.getStateFlow(USER_ADDRESS_SCREEN , addressData)?.collect{ getAddressData ->
            addressData = getAddressData
        }
    }


    val viewModelUiState = viewModel.viewModelUI.collectAsState()

    var isRefreshing by remember { mutableStateOf(false) }

    var state by remember { mutableIntStateOf(1) }

    var listProductItem by remember { mutableStateOf((emptyList<ProductCartItem> ())) }

    var totalPrice by remember { mutableFloatStateOf(0f) }

    when(viewModelUiState.value){
        is CheckOutScreenState.Failure -> {
            state = 0
        }

        is CheckOutScreenState.Loading -> {
            state = 1
        }

        is CheckOutScreenState.Success -> {
            state = 2

            listProductItem = (viewModelUiState.value as CheckOutScreenState.Success).data

            totalPrice = viewModel.calculateTotalPrice(listProductItem)
        }

        is CheckOutScreenState.MakeOrderDone -> {
            state = 3
        }
    }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        state = rememberPullToRefreshState(),
        onRefresh = {
            viewModel.viewModelScope.launch {
                isRefreshing = true
                viewModel.getProductCart()
                isRefreshing = false
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Arrow Back"
                    )
                }
            }

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Cart Summary" , style = MyTypography.headlineMedium)
            }

            Spacer(modifier = Modifier.height(15.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
                    .clickable {
                        navController.navigate(AddressScreen)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.size(35.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ){
                    Image(
                        painter = painterResource(R.drawable.ic_address),
                        contentDescription = "Address Logo"
                    )
                }

                Spacer(modifier = Modifier.width(5.dp))

                Column {
                    Text(text = "Shipping Address" , style = MyTypography.bodyMedium)
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = addressData.addressLine + ", " + addressData.government + ", " + addressData.country,
                        style = MyTypography.bodyLarge,
                        color = Color.LightGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            if(state == 0){
                val context = LocalContext.current
                LaunchedEffect(true) {
                    Toast.makeText(context , "Error refresh the screen" , Toast.LENGTH_LONG).show()
                }
            }

            if(state == 1){
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Text(text = "Loading..." , style = MyTypography.bodyMedium)
                }
            }

            if(state == 2){
                var isVisible by remember { mutableStateOf(false) }

                LaunchedEffect(true) {
                    isVisible = true
                }

                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn() + expandVertically()
                ) {
                    Box(
                        modifier = Modifier.clip(RoundedCornerShape(15.dp))
                            .background(Color.LightGray)
                    ){
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth()
                                .padding(5.dp)
                        ) {
                            item {
                                Text(text = "Products:" , style = MyTypography.headlineSmall)

                                Spacer(modifier = Modifier.height(10.dp))
                            }

                            items(listProductItem){ productItem ->

                                Row(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = productItem.productName,
                                        style = MyTypography.bodyLarge,
                                        maxLines = 1,
                                        overflow = TextOverflow.Clip,
                                        modifier = Modifier.padding(start = 5.dp)
                                    )

                                    Box(modifier = Modifier.weight(1f))

                                    Text(
                                        text = "$" + productItem.price + " x" + productItem.quantity,
                                        style = MyTypography.bodyLarge,
                                        maxLines = 1,
                                        overflow = TextOverflow.Clip
                                    )
                                }

                                Spacer(modifier = Modifier.height(10.dp))
                            }

                            item {
                                Spacer(modifier = Modifier.height(15.dp))

                                Text(text = "Amount:" , style = MyTypography.headlineSmall)

                                Spacer(modifier = Modifier.height(10.dp))

                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(start = 5.dp)
                                ) {
                                    Amount("SubTotal" , "$" + DecimalFormat("#,###").format(totalPrice))
                                    Amount("Tax" , "$" + (299.6).toString())
                                    Amount("Shipping" , "$" + (5.0).toString())
                                    Amount("Discount" , "$" + (149.8).toString())
                                    Amount("Total" , "$" + DecimalFormat("#,###").format(totalPrice + 299.6 + 5.0 - 149.8))
                                }
                            }
                        }
                    }
                }
            }

            if(state == 3){
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        modifier = Modifier.size(300.dp),
                        painter = painterResource(R.drawable.adding_order_successfully),
                        contentDescription = "Adding order successfully",
                        contentScale = ContentScale.FillBounds
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(text = "Order added Successfully!!" , style = MyTypography.bodyMedium)
                }
            }

            Box(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    viewModel.makeOrder(AddressData.fromAddressData(addressData))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                enabled = addressData.addressLine.isNotEmpty() && addressData.government.isNotEmpty() &&
                        addressData.postalCode.isNotEmpty() && addressData.country.isNotEmpty()
            ) {
                Text(text = "Check Out")
            }
        }
    }
}

@Composable
fun Amount(name: String , price: String) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = name,
            style = MyTypography.bodyLarge,
            maxLines = 1,
            overflow = TextOverflow.Clip
        )

        Box(modifier = Modifier.weight(1f))

        Text(
            text = price,
            style = MyTypography.bodyLarge,
            maxLines = 1,
            overflow = TextOverflow.Clip
        )
    }

    Spacer(modifier = Modifier.height(10.dp))
}