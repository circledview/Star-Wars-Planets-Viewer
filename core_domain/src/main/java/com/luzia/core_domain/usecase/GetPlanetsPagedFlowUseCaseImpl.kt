package com.luzia.core_domain.usecase

import androidx.paging.PagingData
import com.luzia.core_domain.model.PlanetSummary
import com.luzia.core_domain.repository.PlanetsRepository
import kotlinx.coroutines.flow.Flow

/**
 * Implementation of [GetPlanetsPagedFlowUseCase] that retrieves a paged flow of planet summaries
 * from the [PlanetsRepository].
 *
 * @property repository The repository responsible for providing planet data.
 */
internal class GetPlanetsPagedFlowUseCaseImpl(private val repository: PlanetsRepository) :
    GetPlanetsPagedFlowUseCase {

    override operator fun invoke(): Flow<PagingData<PlanetSummary>> {
        return repository.getPlanetsPaged()
    }
}