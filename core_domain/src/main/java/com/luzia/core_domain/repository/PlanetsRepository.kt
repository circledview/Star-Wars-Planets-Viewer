package com.luzia.core_domain.repository

import androidx.paging.PagingData
import com.luzia.core_domain.model.PlanetProperties
import com.luzia.core_domain.model.PlanetSummary
import kotlinx.coroutines.flow.Flow

interface PlanetsRepository {
    fun getPlanetsPaged(): Flow<PagingData<PlanetSummary>>
    suspend fun getPlanetDetail(uid: String): PlanetProperties
}