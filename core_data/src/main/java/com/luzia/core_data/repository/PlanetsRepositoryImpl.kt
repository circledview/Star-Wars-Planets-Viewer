package com.luzia.core_data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.luzia.core_data.api.SwApi
import com.luzia.core_data.dto.toPlanetProperties
import com.luzia.core_data.paging.PlanetsPagingSource
import com.luzia.core_domain.model.PlanetProperties
import com.luzia.core_domain.model.PlanetSummary
import com.luzia.core_domain.repository.PlanetsRepository
import com.luzia.core_domain.utils.CoroutineDispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

internal class PlanetsRepositoryImpl(
    private val swApi: SwApi,
    private val planetsPagingSource: PlanetsPagingSource,
    private val dispatcherProvider: CoroutineDispatcherProvider
) : PlanetsRepository {

    /**
     * Returns a flow of paged PlanetSummary data using the Paging 3 library.
     * Uses a fixed page size of 10 and disables placeholders.
     */
    override fun getPlanetsPaged(): Flow<PagingData<PlanetSummary>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { planetsPagingSource }
        ).flow
    }

    private val planetCache = mutableMapOf<String, PlanetProperties>()

    /**
     * Retrieves detailed planet information by UID.
     * - Uses a cache to avoid redundant API calls.
     * - If not cached, fetches from the API and converts to domain model.
     * - Executed on the IO dispatcher.
     */
    override suspend fun getPlanetDetail(uid: String): PlanetProperties {
        return withContext(dispatcherProvider.io) {
            planetCache.getOrPut(uid) {
                val response = swApi.getPlanetDetails(uid)
                val dto = response.result.properties
                dto.toPlanetProperties()
            }
        }
    }
}