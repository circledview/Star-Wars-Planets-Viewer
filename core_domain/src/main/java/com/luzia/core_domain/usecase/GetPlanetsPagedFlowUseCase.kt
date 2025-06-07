package com.luzia.core_domain.usecase

import androidx.paging.PagingData
import com.luzia.core_domain.model.PlanetSummary
import kotlinx.coroutines.flow.Flow

interface GetPlanetsPagedFlowUseCase {

    operator fun invoke(): Flow<PagingData<PlanetSummary>>
}
