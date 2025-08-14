package com.example.e_commerce.ui.feature.account.save_user_data

import android.content.Context
import com.example.domain.response.user.UserResponse
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object SaveRestoreUserData: KoinComponent {
    private val context: Context by inject()

    fun storeUser(user: UserResponse){
        val sharedPreferences = context.getSharedPreferences("user" , Context.MODE_PRIVATE)

        with(sharedPreferences.edit()){
            putInt("id" , user.id!!)
            putString("username" , user.username)
            putString("email" , user.email)
            putString("name" , user.name)
            apply()
        }
    }

    fun getUser(): UserResponse?{
        val sharedPreferences = context.getSharedPreferences("user" , Context.MODE_PRIVATE)

        with(sharedPreferences){
            val id = getInt("id" , 0)
            val username = getString("username" , null)
            val name = getString("name" , null)
            val email = getString("email" , null)

            return if(id != 0 && username != null && name != null && email != null)
                UserResponse(id , username , email , name)
            else null
        }
    }
}