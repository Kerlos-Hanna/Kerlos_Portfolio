package com.example.countryquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(statusBarStyle = SystemBarStyle.light(
            scrim = Color(0xFFB567E5).toArgb(),
            darkScrim = Color.Transparent.toArgb()
        ))

        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController , startDestination = "HomePage"){
                composable(route = "HomePage") {
                    HomePage(navController)
                }

                composable(
                    route = "Quiz/{name}",
                    arguments = listOf(navArgument("name"){type = NavType.StringType})
                ) { backStackEntry->
                    val name = backStackEntry.arguments?.getString("name")
                    Quiz(navController , name)
                }

                composable(
                    route = "ResultPage/{name}/{points}",
                    arguments = listOf(
                        navArgument("name"){type = NavType.StringType},
                        navArgument("points"){type = NavType.IntType}
                    )
                ) { backStackEntry ->
                    val name = backStackEntry.arguments?.getString("name")
                    val points = backStackEntry.arguments?.getInt("points")
                    ResultPage(navController , name , points)
                }
            }
        }
    }
}