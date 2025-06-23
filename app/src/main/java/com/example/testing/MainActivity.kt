package com.example.testing

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column {
                var progress by remember { mutableFloatStateOf(0f) }

                ProfileHeader(progress)
                Spacer(modifier = Modifier.height(32.dp))
                Slider(
                    value = progress,
                    onValueChange = {
                        progress = it
                    },
                    modifier = Modifier.padding(horizontal = 32.dp)
                )
            }
        }

        Log.d("Printing" , "OnCreate")
    }

    override fun onResume() {
        super.onResume()

        Log.d("Printing" , "OnResume")
    }

    override fun onStop() {
        super.onStop()

        Log.d("Printing" , "OnStop")
    }

    override fun onPause() {
        super.onPause()

        Log.d("Printing" , "OnPause")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d("Printing" , "OnDestroy")
    }
}