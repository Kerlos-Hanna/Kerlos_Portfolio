package com.example.e_commerce

import android.app.Application
import com.example.data.di.dataModule
import com.example.domain.di.domainModule
import com.example.e_commerce.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ECommerceApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ECommerceApp)
            modules(listOf(
                presentationModule,
                domainModule,
                dataModule
            ))
        }
    }
}