package com.example.e_commerce.ui.feature.home

import android.Manifest
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.domain.model.Product
import com.example.e_commerce.R
import com.example.e_commerce.navigation.HomeScreen
import com.example.e_commerce.navigation.ProductDetails
import com.example.e_commerce.ui.model.UIProduct
import com.example.e_commerce.ui.theme.MyTypography
import com.example.e_commerce.ui.theme.PurpleBottomSheet
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    selectedBar: MutableState<Int>,
    paddingValues: PaddingValues,
    viewModel: HomeScreenViewModel = koinViewModel()
) {

    val homeScreenUIState = koinViewModel<HomeScreenViewModel>().homeScreenUIState.collectAsState()
    var uriImage by remember { mutableStateOf<String?>(null) }
    var textFieldInput by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableIntStateOf(0) }
    val imagePicker = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
        if(uri != null){
            uriImage = uri.toString()
        }
    }

    val requestPermission = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted ->
        if(isGranted){
            imagePicker.launch("image/*")
        }
    }
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
    ) {
        TopBar(requestPermission , uriImage , viewModel)

        Spacer(modifier = Modifier.height(5.dp))

        TextField(
            value = textFieldInput,
            onValueChange = {
                textFieldInput = it
            },
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.White,
                focusedIndicatorColor = Color.White,
                focusedContainerColor = Color(0xffededed),
                unfocusedContainerColor = Color(0xffededed)
            ),
            placeholder = {
                Text(text = "Search here" , color = Color(0xFFa5a5a5))
            },
            prefix = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon"
                )
            },
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(5.dp))

        when(homeScreenUIState.value) {
            is HomeScreenUIEvents.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        CircularProgressIndicator()
                        Text(text = "Loading...")
                    }
                }
            }

            is HomeScreenUIEvents.Success -> {

                var isVisible by remember { mutableStateOf(false) }

                LaunchedEffect(true) {
                    isVisible = true
                }

                AnimatedVisibility(visible = isVisible , enter = fadeIn() + expandVertically()) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        item {
                            LazyRow {

                                val list = (homeScreenUIState.value as HomeScreenUIEvents.Success).categories

                                items((homeScreenUIState.value as HomeScreenUIEvents.Success).categories.size){ index ->
                                    Button(
                                        onClick = {
                                            selectedCategory = index
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if(selectedCategory == index) PurpleBottomSheet else  Color(0xFFa5a5a5)
                                        )
                                    ) {
                                        Text(text = list[index].title.replaceFirstChar { it.uppercaseChar() } , style = MyTypography.bodyMedium)
                                    }

                                    Spacer(modifier = Modifier.width(5.dp))
                                }
                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            Text(text = "Feature", style = MyTypography.headlineMedium)
                            LazyRow {
                                items((homeScreenUIState.value as HomeScreenUIEvents.Success).feature) { product ->
                                    ProductItem(product , navController , selectedBar)
                                }
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            Text(text = "Most Popular", style = MyTypography.headlineMedium)
                            LazyRow {
                                items((homeScreenUIState.value as HomeScreenUIEvents.Success).mostPopular) { product ->
                                    ProductItem(product , navController , selectedBar)
                                }
                            }
                        }
                    }
                }
            }

            is HomeScreenUIEvents.Error -> {
                Text(text = "Error happened")
            }
        }
    }
}

@Composable
fun ProductItem(product: Product , navController: NavController , selectedBar: MutableState<Int>) {
    Card(
        onClick = {
            selectedBar.value = 0

            navController.navigate(ProductDetails(UIProduct.fromProduct(product))){
                popUpTo(HomeScreen)
            }
        },
        modifier = Modifier
            .size(200.dp)
            .clip(RoundedCornerShape(10.dp))
            .padding(end = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFededed)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {

            Image(
                painter = rememberAsyncImagePainter(product.image),
                contentDescription = "Product Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentScale = ContentScale.FillBounds
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = product.title,  maxLines = 1 , style = MyTypography.bodyMedium)

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "$${product.price}" , color = PurpleBottomSheet)
        }
    }
}

@Composable
fun TopBar(requestPermission: ManagedActivityResultLauncher<String , Boolean> , uriImage: String? , viewModel: HomeScreenViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                requestPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            },
            modifier = Modifier.size(100.dp)
        ) {
            Image(
                painter = if(uriImage == null) painterResource(R.drawable.camera) else rememberAsyncImagePainter(uriImage),
                contentDescription = "Logo Profile",
                modifier = Modifier.clip(CircleShape)
                    .size(100.dp),
                contentScale = ContentScale.FillBounds
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column {
            Text(text = "Hello!")
            Text(text = viewModel.sharedPreferences.getString("name" , null).toString())
        }

        Box(modifier = Modifier.weight(1f))

        IconButton(
            onClick = {

            },
            modifier = Modifier.clip(CircleShape)
                .background(Color.LightGray)
        ) {
            Icon(
                imageVector = Icons.Filled.Notifications,
                contentDescription = "Notification",
            )
        }
    }
}