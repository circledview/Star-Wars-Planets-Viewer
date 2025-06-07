package com.luzia.core_domain.usecase

import androidx.paging.PagingData
import com.luzia.core_domain.model.PlanetSummary
import com.luzia.core_domain.repository.PlanetsRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetPlanetsPagedFlowUseCaseImplTest {
    private val repository: PlanetsRepository = mockk()
    private val useCase = GetPlanetsPagedFlowUseCaseImpl(repository)

    @Test
    fun `invoke returns flow from repository`() = runTest {
        val expectedPagingData = PagingData.from(
            listOf(
                PlanetSummary("Tatooine", "url1", url = "url"),
                PlanetSummary("Hoth", "url2", url = "utl")
            )
        )
        every { repository.getPlanetsPaged() } returns flowOf(expectedPagingData)

        val result = useCase().first()

        assertEquals(expectedPagingData, result)
    }
}