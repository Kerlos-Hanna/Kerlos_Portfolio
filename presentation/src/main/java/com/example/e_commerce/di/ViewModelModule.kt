package com.example.e_commerce.di

import com.example.e_commerce.ui.feature.account.login.LoginScreenViewModel
import com.example.e_commerce.ui.feature.account.register.RegisterScreenViewModel
import com.example.e_commerce.ui.feature.cart.CartScreenViewModel
import com.example.e_commerce.ui.feature.check_out.CheckOutScreenViewModel
import com.example.e_commerce.ui.feature.home.HomeScreenViewModel
import com.example.e_commerce.ui.feature.orders.OrdersScreenViewModel
import com.example.e_commerce.ui.feature.product_detail.ProductDetailsScreenViewModel
import com.example.e_commerce.ui.feature.profile.ProfileScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        HomeScreenViewModel(get() , get())
    }

    viewModel {
        ProductDetailsScreenViewModel(get())
    }

    viewModel {
        CartScreenViewModel(get() , get() , get() , get())
    }

    viewModel {
        CheckOutScreenViewModel(get() , get())
    }

    viewModel {
        OrdersScreenViewModel(get() , get())
    }

    viewModel {
        LoginScreenViewModel(get())
    }

    viewModel {
        RegisterScreenViewModel(get())
    }

    viewModel {
        ProfileScreenViewModel()
    }
}