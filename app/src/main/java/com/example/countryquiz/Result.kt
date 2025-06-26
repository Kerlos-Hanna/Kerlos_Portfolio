package com.example.countryquiz

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ResultPage(navController: NavController , name: String? , pointsOfTheGame: Int?) {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFB567E5), Color(0xFF7b0daf))
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Result",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Image(
                painter = painterResource(R.drawable.trophy),
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Congratulation!",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = name!!,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Your score is $pointsOfTheGame out of 10",
                fontSize = 20.sp,
                fontWeight = FontWeight.Light,
                color = Color.LightGray
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    navController.navigate(route = "HomePage"){
                        popUpTo(0){
                            inclusive = true
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                )
            ) {
                Text(text = "FINISH" , color = Color.Magenta , fontSize = 20.sp)
            }
        }
    }
}