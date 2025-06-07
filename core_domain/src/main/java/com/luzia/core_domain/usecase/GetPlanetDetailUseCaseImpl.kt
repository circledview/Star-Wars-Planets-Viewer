package com.luzia.core_domain.usecase

import com.luzia.core_domain.model.PlanetProperties
import com.luzia.core_domain.repository.PlanetsRepository

internal class GetPlanetDetailUseCaseImpl(private val repository: PlanetsRepository) :
    GetPlanetDetailUseCase {

    override suspend operator fun invoke(uid: String): PlanetProperties {
        return repository.getPlanetDetail(uid)
    }

}