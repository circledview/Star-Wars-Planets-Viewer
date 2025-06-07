package com.luzia.feature_planets_list.di

import com.luzia.feature_planets_list.viewmodel.PlanetsListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val planetsModule = module {
    viewModel { PlanetsListViewModel(get(), get()) }
}