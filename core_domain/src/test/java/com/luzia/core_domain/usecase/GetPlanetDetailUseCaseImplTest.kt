package com.luzia.core_domain.usecase

import com.luzia.core_domain.model.PlanetProperties
import com.luzia.core_domain.repository.PlanetsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetPlanetDetailUseCaseImplTest {

    private val repository: PlanetsRepository = mockk<PlanetsRepository>()

    @Test
    fun `getPlanetDetailsUseCase returns detail from repository`() = runTest {
        val detail = PlanetProperties(
            name = "Tatooine",
            climate = "Arid",
            population = "200000",
            diameter = "10465",
            gravity = "1 standard",
            terrain = "desert",
            url = "https://swapi.dev/api/planets/1/",
            surfaceWater = "1",
            rotationPeriod = "23",
            orbitalPeriod = "304",
            created = "2024-01-01",
            edited = "2025-01-01"
        )
        coEvery { repository.getPlanetDetail("123") } returns detail

        val useCase = GetPlanetDetailUseCaseImpl(repository)
        val result = useCase("123")

        assertEquals(detail, result)
    }

}