package com.example.e_commerce

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.e_commerce.navigation.AddressScreen
import com.example.e_commerce.navigation.CartScreen
import com.example.e_commerce.navigation.CheckOutScreen
import com.example.e_commerce.navigation.CustomNavType
import com.example.e_commerce.navigation.HomeScreen
import com.example.e_commerce.navigation.LoginScreen
import com.example.e_commerce.navigation.OrdersScreen
import com.example.e_commerce.navigation.ProductDetails
import com.example.e_commerce.navigation.ProfileScreen
import com.example.e_commerce.navigation.RegisterScreen
import com.example.e_commerce.ui.feature.account.login.LoginScreen
import com.example.e_commerce.ui.feature.account.register.RegisterScreen
import com.example.e_commerce.ui.feature.account.save_user_data.SaveRestoreUserData
import com.example.e_commerce.ui.feature.address_data.AddressScreen
import com.example.e_commerce.ui.feature.cart.CartScreen
import com.example.e_commerce.ui.feature.check_out.CheckOutScreen
import com.example.e_commerce.ui.feature.home.HomeScreen
import com.example.e_commerce.ui.feature.orders.OrdersScreen
import com.example.e_commerce.ui.feature.product_detail.ProductDetailsScreen
import com.example.e_commerce.ui.feature.profile.ProfileScreen
import com.example.e_commerce.ui.model.AddressData
import com.example.e_commerce.ui.model.UIProduct
import kotlin.reflect.typeOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val selectedBar = remember { mutableIntStateOf(0) }
            val navController = rememberNavController()
            var shouldHaveNavigationBottom by remember { mutableStateOf(true) }

            Scaffold(
                modifier = Modifier.fillMaxSize().background(Color.Black),
                bottomBar = {
                    if(shouldHaveNavigationBottom)
                        NavigationBar {
                            NavigationBarItem(
                                onClick = {
                                    selectedBar.intValue = 0

                                    val route = navController.currentBackStackEntry?.destination?.route?.substringAfterLast(".")!!

                                    if(route != "HomeScreen"){
                                        navController.popBackStack()
                                    }
                                },
                                selected = selectedBar.intValue == 0,
                                label = {
                                    Text(text = "Home")
                                },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.Home,
                                        contentDescription = "Home Icon"
                                    )
                                }
                            )

                            NavigationBarItem(
                                onClick = {
                                    selectedBar.intValue = 1

                                    navController.navigate(CartScreen){
                                        popUpTo(HomeScreen)
                                    }
                                },
                                selected = selectedBar.intValue == 1,
                                label = {
                                    Text(text = "Category")
                                },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.ShoppingCart,
                                        contentDescription = "Category Icon"
                                    )
                                }
                            )

                            NavigationBarItem(
                                onClick = {
                                    selectedBar.intValue = 2

                                    navController.navigate(OrdersScreen){
                                        popUpTo(HomeScreen)
                                    }
                                },
                                selected = selectedBar.intValue == 2,
                                label = {
                                    Text(text = "Orders")
                                },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.Favorite,
                                        contentDescription = "Order Icon"
                                    )
                                }
                            )

                            NavigationBarItem(
                                onClick = {
                                    selectedBar.intValue = 3

                                    navController.navigate(ProfileScreen){
                                        popUpTo(HomeScreen)
                                    }
                                },
                                selected = selectedBar.intValue == 3,
                                label = {
                                    Text(text = "Profile")
                                },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = "Profile Icon"
                                    )
                                }
                            )
                        }

                }
            ) { paddingValues ->

                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    NavHost(navController, startDestination = if(SaveRestoreUserData.getUser() != null) HomeScreen
                    else LoginScreen) {
                        composable<HomeScreen> {
                            shouldHaveNavigationBottom = true
                            HomeScreen(navController , selectedBar , paddingValues)
                        }

                        composable<CartScreen> {
                            CartScreen(navController , paddingValues)
                        }

                        composable<CheckOutScreen> (
                            typeMap = mapOf(
                                typeOf<AddressData>() to CustomNavType.addressData
                            )
                        ){
                            CheckOutScreen(navController , paddingValues)
                        }

                        composable<ProductDetails>(
                            typeMap = mapOf(
                                typeOf<UIProduct>() to CustomNavType.ProductType
                            )
                        ){
                            shouldHaveNavigationBottom = false
                            val args = it.toRoute<ProductDetails>()
                            ProductDetailsScreen(navController , args.product , paddingValues)
                        }

                        composable<AddressScreen> {
                            AddressScreen(navController , paddingValues)
                        }

                        composable<ProfileScreen> {
                            ProfileScreen(paddingValues)
                        }

                        composable<OrdersScreen> {
                            OrdersScreen(paddingValues)
                        }

                        composable<RegisterScreen> {
                            shouldHaveNavigationBottom = false
                            RegisterScreen(navController , paddingValues)
                        }

                        composable<LoginScreen> {
                            shouldHaveNavigationBottom = false
                            LoginScreen(navController , paddingValues)
                        }
                    }
                }
            }
        }
    }
}