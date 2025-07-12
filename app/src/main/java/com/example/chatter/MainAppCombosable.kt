package com.example.chatter

import android.app.Application
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatter.feature.auth.signin.SignInScreen
import com.example.chatter.feature.auth.signup.SignUpScreen
import com.example.chatter.feature.chat.ChatScreen
import com.example.chatter.feature.chat.ShownImageFullScreen
import com.example.chatter.feature.home.HomeScreen
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.HiltAndroidApp

@Composable
fun MainApp() {
    Box(modifier = Modifier.fillMaxSize()) {
        val navController = rememberNavController()
        val currentUser = FirebaseAuth.getInstance().currentUser
        val startDestination = if(currentUser == null) "SignInScreen" else "Home Screen"

        NavHost(navController = navController, startDestination = startDestination) {
            composable(route = "SignInScreen"){
                SignInScreen(navController)
            }

            composable(route = "SignUpScreen"){
                SignUpScreen(navController)
            }

            composable(route = "Home Screen"){
                HomeScreen(navController)
            }

            composable(route = "ChatScreen/{channelName}/{channelID}"){ backStackEntry ->

                val channelName = backStackEntry.arguments?.getString("channelName")
                val channelID = backStackEntry.arguments?.getString("channelID")

                ChatScreen(navController , channelName!! , channelID!!)
            }

            composable(route = "ImageFullScreen/{url}"){ backStackEntry ->
                val url = backStackEntry.arguments?.getString("url")

                ShownImageFullScreen(url)
            }
        }
    }
}

@HiltAndroidApp
class ChatterApp: Application()