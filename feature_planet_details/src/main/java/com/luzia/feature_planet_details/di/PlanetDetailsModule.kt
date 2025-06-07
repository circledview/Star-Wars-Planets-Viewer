package com.luzia.feature_planet_details.di

import com.luzia.feature_planet_details.viewmodel.PlanetDetailViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val planetDetailsModule = module {
    viewModel { (uid: String) ->
        PlanetDetailViewModel(get(), uid)
    }
}