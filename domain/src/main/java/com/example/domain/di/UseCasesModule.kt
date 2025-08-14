package com.example.domain.di

import com.example.domain.use_cases.AddProductToCartUseCase
import com.example.domain.use_cases.DecrementQuantityUseCase
import com.example.domain.use_cases.DeleteOrderUseCase
import com.example.domain.use_cases.DeleteProductCartUseCase
import com.example.domain.use_cases.GetCategoryUseCase
import com.example.domain.use_cases.GetOrdersUseCase
import com.example.domain.use_cases.GetProductCartUseCase
import com.example.domain.use_cases.GetProductUseCase
import com.example.domain.use_cases.IncrementQuantityUseCase
import com.example.domain.use_cases.LoginUseCase
import com.example.domain.use_cases.MakeOrderUseCase
import com.example.domain.use_cases.RegisterUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory {
        GetProductUseCase(get())
    }

    factory {
        GetCategoryUseCase(get())
    }

    factory {
        AddProductToCartUseCase(get())
    }

    factory {
        GetProductCartUseCase(get())
    }

    factory {
        DeleteProductCartUseCase(get())
    }

    factory {
        IncrementQuantityUseCase(get())
    }

    factory {
        DecrementQuantityUseCase(get())
    }

    factory {
        MakeOrderUseCase(get())
    }

    factory {
        GetOrdersUseCase(get())
    }

    factory {
        DeleteOrderUseCase(get())
    }

    factory {
        LoginUseCase(get())
    }

    factory {
        RegisterUseCase(get())
    }
}