package com.luzia.core_data.api

import com.luzia.core_data.dto.PlanetDetailResponseDto
import com.luzia.core_data.dto.PlanetListResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface SwApi {

    @GET("planets")
    suspend fun getPlanets(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 10
    ): PlanetListResponseDto

    @GET("planets/{uid}")
    suspend fun getPlanetDetails(
        @Path("uid") uid: String
    ): PlanetDetailResponseDto
}