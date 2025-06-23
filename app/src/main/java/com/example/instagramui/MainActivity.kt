package com.example.instagramui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            enableEdgeToEdge(navigationBarStyle = SystemBarStyle.dark(Color.Black.toArgb()))

            Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
                Column(modifier = Modifier.background(Color.White).padding(start = 10.dp , end = 10.dp)) {
                    TopBar()

                    ProfileStatus()

                    Spacer(modifier = Modifier.height(5.dp))

                    ProfileDescription()

                    Spacer(modifier = Modifier.height(10.dp))

                    ActionButtons()

                    Spacer(modifier = Modifier.height(20.dp))

                    StatuesInstagram()

                    Spacer(modifier = Modifier.height(20.dp))
                }

                PostsTabs()
            }
        }
    }
}