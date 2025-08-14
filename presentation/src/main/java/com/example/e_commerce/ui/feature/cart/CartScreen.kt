package com.example.e_commerce.ui.feature.cart

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.domain.request.add_product.DataItem
import com.example.domain.response.get_product_cart.ProductCartItem
import com.example.e_commerce.navigation.CheckOutScreen
import com.example.e_commerce.ui.model.AddressData
import com.example.e_commerce.ui.theme.MyTypography
import com.example.e_commerce.ui.theme.PurpleBottomSheet
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavController , paddingValues: PaddingValues , viewModel: CartScreenViewModel = koinViewModel()) {

    val viewModelUiState = viewModel.viewModelUI.collectAsState()

    var isRefreshing by remember { mutableStateOf(false) }

    var state by remember { mutableIntStateOf(1) }

    var listProductItem by remember { mutableStateOf((emptyList<ProductCartItem> ())) }

    when(viewModelUiState.value){
        is CartScreenState.Failure -> {
            state = 0
        }

        is CartScreenState.Loading -> {
            state = 1
        }

        is CartScreenState.Success -> {
            state = 2

            listProductItem = (viewModelUiState.value as CartScreenState.Success).data
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
        ) {
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
                    if(listProductItem.isNotEmpty()){
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.9f)
                        ) {
                            items(listProductItem){ productItem ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(100.dp)
                                        .padding(horizontal = 16.dp)
                                        .clip(RoundedCornerShape(15.dp))
                                        .background(Color(0xFFf0d7ef))
                                ) {
                                    Image(
                                        painter = rememberAsyncImagePainter(productItem.imageUrl),
                                        contentDescription = "Product image",
                                        modifier = Modifier
                                            .size(100.dp)
                                            .clip(
                                                RoundedCornerShape(
                                                    topStart = 15.dp,
                                                    bottomStart = 15.dp
                                                )
                                            ),
                                        contentScale = ContentScale.FillBounds
                                    )

                                    Spacer(modifier = Modifier.width(5.dp))

                                    Column(
                                        modifier = Modifier.fillMaxWidth(0.5f)
                                    ) {
                                        Text(
                                            text = productItem.productName,
                                            style = MyTypography.bodyMedium,
                                            maxLines = 1,
                                            overflow = TextOverflow.Clip
                                        )

                                        Text(
                                            text = "$" + productItem.price.toString(),
                                            style = MyTypography.bodyMedium,
                                            color = PurpleBottomSheet
                                        )
                                    }

                                    Box(modifier = Modifier.weight(1f))

                                    Column(
                                        modifier = Modifier.fillMaxHeight()
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Box(modifier = Modifier.weight(1f))

                                            IconButton(
                                                onClick = {
                                                    viewModel.viewModelScope.launch {
                                                        viewModel.deleteProductCart(productItem.id)
                                                    }
                                                }
                                            ) {
                                                Icon(
                                                    tint = Color.Red,
                                                    imageVector = Icons.Default.Delete,
                                                    contentDescription = "Delete product icon",
                                                )
                                            }
                                        }

                                        Box(modifier = Modifier.weight(1f))

                                        Row(
                                            horizontalArrangement = Arrangement.Center,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Box(modifier = Modifier.weight(1f))

                                            val context = LocalContext.current

                                            IconButton(
                                                onClick = {
                                                    viewModel.viewModelScope.launch {
                                                        if(productItem.quantity > 1){
                                                            productItem.quantity -= 1
                                                            viewModel.decrementQuantity(DataItem.fromProductCartItem(productItem))
                                                        }

                                                        else{
                                                            Toast.makeText(context , "Cannot be less than 1" , Toast.LENGTH_LONG).show()
                                                        }
                                                    }
                                                }
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .size(30.dp)
                                                        .clip(CircleShape)
                                                        .background(PurpleBottomSheet),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Text(text = "-", fontSize = 25.sp)
                                                }
                                            }

                                            Text(text = productItem.quantity.toString())

                                            IconButton(
                                                onClick = {
                                                    if(productItem.quantity < 10) {
                                                        viewModel.viewModelScope.launch {
                                                            productItem.quantity += 1
                                                            viewModel.incrementQuantity(
                                                                DataItem.fromProductCartItem(
                                                                    productItem
                                                                )
                                                            )
                                                        }
                                                    }
                                                    else{
                                                        Toast.makeText(context , "Cannot be more than 10" , Toast.LENGTH_LONG).show()
                                                    }

                                                }
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .size(30.dp)
                                                        .clip(CircleShape)
                                                        .background(PurpleBottomSheet),
                                                    contentAlignment = Alignment.Center
                                                ){
                                                    Text(text = "+", fontSize = 25.sp)
                                                }
                                            }
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(20.dp))
                            }
                        }
                    }

                    else{
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.9f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "No Items in the Cart!")
                        }
                    }
                }
            }

            Box(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    navController.navigate(CheckOutScreen(AddressData(
                        "",
                        "",
                        "",
                        ""
                    )))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                enabled = listProductItem.isNotEmpty()
            ) {
                Text(text = "Check Out")
            }
        }
    }
}