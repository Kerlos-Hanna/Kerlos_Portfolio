package com.example.e_commerce.ui.feature.product_detail

import android.widget.Toast
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.e_commerce.R
import com.example.e_commerce.ui.model.UIProduct
import com.example.e_commerce.ui.theme.MyTypography
import com.example.e_commerce.ui.theme.PurpleBottomSheet
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProductDetailsScreen(
    navController: NavController,
    product: UIProduct,
    paddingValues: PaddingValues,
    viewModel: ProductDetailsScreenViewModel = koinViewModel()
) {
    val viewModelUIState = viewModel.viewModelUI.collectAsState()

    var isFavouriteIcon by remember { mutableStateOf(false) }

    val selectedSizeIndex = remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(product.image),
                    contentDescription = "Product Image",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp)),
                    contentScale = ContentScale.FillBounds
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        },
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.LightGray)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Arrow Back",
                        )
                    }

                    IconButton(
                        onClick = {
                            isFavouriteIcon = !isFavouriteIcon
                        },
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.LightGray)
                    ) {
                        Icon(
                            imageVector = if(isFavouriteIcon) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Arrow Back",
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = product.title,
                    style = MyTypography.headlineMedium,
                    maxLines = 1,
                    modifier = Modifier.weight(4f)
                )

                Text(
                    text = "$${product.price}",
                    style = MyTypography.bodyMedium,
                    color = PurpleBottomSheet,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_star),
                    contentDescription = "Star rating logo"
                )

                Text(text = "4.5" , style = MyTypography.bodyLarge , color = Color.Black)

                Text(text = "(20 Review)" , style = MyTypography.bodyLarge , color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Description",
                style = MyTypography.headlineMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Text(
                text = product.description,
                style = MyTypography.bodyLarge,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Size",
                style = MyTypography.headlineMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                repeat(4){ index ->
                    SizeItems((index+2)*(index+2) , index ,selectedSizeIndex)
                }
            }

            Box(modifier = Modifier.weight(1f)) {  }

            Row(
                modifier = Modifier.fillMaxWidth()
            ){
                Button(
                    onClick = {

                    },
                    modifier = Modifier.weight(2f)
                        .padding(start = 10.dp)
                ) {
                    Text(
                        text = "Buy Now",
                        style = MyTypography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                when(viewModelUIState.value){
                    is DetailsScreenState.Error -> {
                        Toast.makeText(LocalContext.current , "Error" , Toast.LENGTH_LONG).show()
                    }
                    DetailsScreenState.Loading -> {}
                    DetailsScreenState.Nothing -> {}
                    is DetailsScreenState.Success -> {
                        val context = LocalContext.current
                        LaunchedEffect(true) {
                            Toast.makeText(context , "Successfully added to cart" , Toast.LENGTH_LONG).show()
                        }
                    }
                }

                Button(
                    onClick = {
                        viewModel.addProductToCart(product)
                    },
                    modifier = Modifier.weight(0.8f)
                        .padding(end = 10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFe6e6e6),
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ShoppingCart,
                        contentDescription = "Shopping Cart",
                    )
                }
            }
        }

        if(viewModelUIState.value == DetailsScreenState.Loading){
            Column(
                modifier = Modifier.fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.8f)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
                Text(text = "Loading..." , style = MyTypography.bodyMedium , color = Color.White)
            }
        }
    }
}

@Composable
fun SizeItems(size: Int , currentIndex: Int , selectedSizeIndex: MutableState<Int>) {
    Button(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.size(80.dp)
            .padding(horizontal = 5.dp),
        onClick = {
            selectedSizeIndex.value = currentIndex
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = if(currentIndex != selectedSizeIndex.value) Color(0xFFe6e6e6) else PurpleBottomSheet
        )
    ){
        Text(text = size.toString() , style = MyTypography.bodyMedium , color = Color.Black)
    }
}