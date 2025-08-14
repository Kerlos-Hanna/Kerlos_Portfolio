package com.example.e_commerce.di

import org.koin.dsl.module

val presentationModule = module {
    includes(viewModelModule)
}