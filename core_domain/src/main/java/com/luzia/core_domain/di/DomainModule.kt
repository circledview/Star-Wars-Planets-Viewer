package com.luzia.core_domain.di

import com.luzia.core_domain.utils.CoroutineDispatcherProvider
import com.luzia.core_domain.utils.DefaultDispatcherProviderImpl
import org.koin.dsl.module

val domainModule = module {
    single<CoroutineDispatcherProvider> {
        DefaultDispatcherProviderImpl()
    }
}