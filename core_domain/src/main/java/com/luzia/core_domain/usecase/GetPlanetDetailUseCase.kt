package com.luzia.core_domain.usecase

import com.luzia.core_domain.model.PlanetProperties

/**
 * Use case for retrieving detailed information about a specific planet.
 */
interface GetPlanetDetailUseCase {

    suspend operator fun invoke(uid: String): PlanetProperties
}
