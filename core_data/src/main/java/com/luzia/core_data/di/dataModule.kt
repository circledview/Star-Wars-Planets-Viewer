package com.luzia.core_data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.luzia.core_data.api.SwApi
import com.luzia.core_data.paging.PlanetsPagingSource
import com.luzia.core_data.repository.PlanetsRepositoryImpl
import com.luzia.core_domain.repository.PlanetsRepository
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

val dataModule = module {

    single<PlanetsPagingSource> { PlanetsPagingSource(get(), get()) }

    single<PlanetsRepository> { PlanetsRepositoryImpl(get(), get(), get()) }

    single<SwApi> {
        get<Retrofit>().create(SwApi::class.java)
    }

    single<Json> {
        Json {
            isLenient = true
            ignoreUnknownKeys = true
            encodeDefaults = true
            ignoreUnknownKeys = true
        }
    }


    single<Retrofit> {
        Retrofit.Builder()
            .client(get())
            .baseUrl("https://swapi.tech/api/")
            .addConverterFactory(get<Json>().asConverterFactory("application/json".toMediaType()))
            .build()
    }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .callTimeout(30, TimeUnit.SECONDS)
            .build()
    }

}