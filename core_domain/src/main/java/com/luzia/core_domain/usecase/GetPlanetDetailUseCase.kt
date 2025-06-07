package com.luzia.core_domain.usecase

import com.luzia.core_domain.model.PlanetProperties

interface GetPlanetDetailUseCase {

    suspend operator fun invoke(uid: String): PlanetProperties
}
