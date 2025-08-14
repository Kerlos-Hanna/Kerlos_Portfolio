package com.example.data.di

import com.example.data.repository.CategoryRepositoryImplementation
import com.example.data.repository.DecrementQuantityRepositoryImplementation
import com.example.data.repository.DeleteOrderRepositoryImplementation
import com.example.data.repository.DeleteProductCartRepositoryImplementation
import com.example.data.repository.GetOrdersRepositoryImplementation
import com.example.data.repository.IncrementQuantityRepositoryImplementation
import com.example.data.repository.MakeOrderRepositoryImplementation
import com.example.data.repository.ProductCartRepositoryImplementation
import com.example.data.repository.ProductRepositoryImplementation
import com.example.data.repository.ProductToCartRepositoryImplementation
import com.example.data.repository.UserRepositoryImplementation
import com.example.domain.repository.CategoryRepository
import com.example.domain.repository.DecrementQuantityRepository
import com.example.domain.repository.DeleteOrderRepository
import com.example.domain.repository.DeleteProductCartRepository
import com.example.domain.repository.GetOrdersRepository
import com.example.domain.repository.IncrementQuantityRepository
import com.example.domain.repository.MakeOrderRepository
import com.example.domain.repository.ProductCartRepository
import com.example.domain.repository.ProductRepository
import com.example.domain.repository.ProductToCartRepository
import com.example.domain.repository.UserRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<ProductRepository> {
        ProductRepositoryImplementation(get())
    }

    single<CategoryRepository> {
        CategoryRepositoryImplementation(get())
    }

    single<ProductToCartRepository> {
        ProductToCartRepositoryImplementation(get())
    }

    single<ProductCartRepository> {
        ProductCartRepositoryImplementation(get())
    }

    single<DeleteProductCartRepository> {
        DeleteProductCartRepositoryImplementation(get())
    }

    single<IncrementQuantityRepository> {
        IncrementQuantityRepositoryImplementation(get())
    }

    single<DecrementQuantityRepository> {
        DecrementQuantityRepositoryImplementation(get())
    }

    single<MakeOrderRepository> {
        MakeOrderRepositoryImplementation(get())
    }

    single<GetOrdersRepository> {
        GetOrdersRepositoryImplementation(get())
    }

    single<DeleteOrderRepository> {
        DeleteOrderRepositoryImplementation(get())
    }

    single<UserRepository> {
        UserRepositoryImplementation(get())
    }
}