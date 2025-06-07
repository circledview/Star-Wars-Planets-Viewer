package com.luzia.core_domain.di

import com.luzia.core_domain.usecase.GetPlanetDetailUseCase
import com.luzia.core_domain.usecase.GetPlanetDetailUseCaseImpl
import com.luzia.core_domain.usecase.GetPlanetsPagedFlowUseCase
import com.luzia.core_domain.usecase.GetPlanetsPagedFlowUseCaseImpl
import com.luzia.core_domain.utils.CoroutineDispatcherProvider
import com.luzia.core_domain.utils.DefaultDispatcherProviderImpl
import org.koin.dsl.module

val domainModule = module {
    single<CoroutineDispatcherProvider> {
        DefaultDispatcherProviderImpl()
    }

    factory<GetPlanetsPagedFlowUseCase> {
        GetPlanetsPagedFlowUseCaseImpl(get())
    }

    factory<GetPlanetDetailUseCase> {
        GetPlanetDetailUseCaseImpl(get())
    }
}