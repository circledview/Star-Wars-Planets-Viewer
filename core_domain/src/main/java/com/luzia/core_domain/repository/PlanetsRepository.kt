package com.luzia.core_domain.repository

import androidx.paging.PagingData
import com.luzia.core_domain.model.PlanetProperties
import com.luzia.core_domain.model.PlanetSummary
import kotlinx.coroutines.flow.Flow

interface PlanetsRepository {
    /**
     * Returns a flow of paged PlanetSummary data using the Paging 3 library.
     * Uses a fixed page size of 10 and disables placeholders.
     */
    fun getPlanetsPaged(): Flow<PagingData<PlanetSummary>>

    /**
     * Retrieves detailed planet information by UID.
     * - Uses a cache to avoid redundant API calls.
     * - If not cached, fetches from the API and converts to domain model.
     * - Executed on the IO dispatcher.
     */
    suspend fun getPlanetDetail(uid: String): PlanetProperties
}