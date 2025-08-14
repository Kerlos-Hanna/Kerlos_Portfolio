package com.example.e_commerce.ui.feature.profile

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ProfileScreenViewModel: ViewModel() , KoinComponent {

    private val context: Context by inject()
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("user" , Context.MODE_PRIVATE)
}