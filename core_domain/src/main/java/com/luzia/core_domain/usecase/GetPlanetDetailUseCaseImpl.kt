package com.luzia.core_domain.usecase

import com.luzia.core_domain.model.PlanetProperties
import com.luzia.core_domain.repository.PlanetsRepository

/**
 * Implementation of [GetPlanetDetailUseCase] that retrieves detailed information for a specific planet.
 */
internal class GetPlanetDetailUseCaseImpl(private val repository: PlanetsRepository) :
    GetPlanetDetailUseCase {

    override suspend operator fun invoke(uid: String): PlanetProperties {
        return repository.getPlanetDetail(uid)
    }

}