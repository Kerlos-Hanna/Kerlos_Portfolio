package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.DeepBlue
import com.example.myapplication.ui.theme.TextWhite
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val statusBar = rememberSystemUiController()

            statusBar.setStatusBarColor(color = DeepBlue)
            statusBar.setNavigationBarColor(color = Color.Black)

            Box(modifier = Modifier.fillMaxSize().background(DeepBlue)){

                Column {
                    Welcoming()

                    AppBarButtons()

                    LargeBox()

                    val gothicaFamily = FontFamily(
                        Font(R.font.gothica1_bold , weight = FontWeight.Bold),
                        Font(R.font.gothica1_regular , weight = FontWeight.Normal)
                    )
                    Text(
                        text = "Featured",
                        style = TextStyle(fontFamily = gothicaFamily , fontWeight = FontWeight.Bold , fontSize = 30.sp , color = TextWhite),
                        modifier = Modifier.padding(top = 50.dp , start = 20.dp)
                    )

                    Spacer(modifier = Modifier.size(16.dp))

                    Box(modifier = Modifier.fillMaxSize()) {

                        BottomNavigationBar()

                    }
                }
            }
        }
    }
}