package com.luzia.core_domain.usecase

import androidx.paging.PagingData
import com.luzia.core_domain.model.PlanetSummary
import kotlinx.coroutines.flow.Flow

/**
 * Use case to retrieve a paginated flow of planet summaries.
 *
 * This interface defines the contract for fetching planet data in a paginated manner,
 * suitable for displaying in a list that loads data on demand.
 */
interface GetPlanetsPagedFlowUseCase {

    operator fun invoke(): Flow<PagingData<PlanetSummary>>
}
