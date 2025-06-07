package com.luzia.core_data.repository

import com.luzia.core_data.api.SwApi
import com.luzia.core_data.dto.PlanetDetailResponseDto
import com.luzia.core_data.dto.PlanetDetailResultDto
import com.luzia.core_data.dto.PlanetPropertiesDto
import com.luzia.core_data.dto.toPlanetProperties
import com.luzia.core_data.paging.PlanetsPagingSource
import com.luzia.core_domain.utils.CoroutineDispatcherProvider
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PlanetsRepositoryImplTest {

    private val swApi: SwApi = mockk()
    private val pagingSource: PlanetsPagingSource = mockk()
    private val dispatcherProvider: CoroutineDispatcherProvider = mockk()

    private lateinit var repository: PlanetsRepositoryImpl

    private val testScheduler = TestCoroutineScheduler()
    private val testDispatcher = StandardTestDispatcher(testScheduler)

    @Before
    fun setup() {
        every { dispatcherProvider.io } returns testDispatcher
        repository = PlanetsRepositoryImpl(swApi, pagingSource, dispatcherProvider)
    }

    @Test
    fun `when getPlanetsPaged, it should return paging flow`() = runTest {
        // Arrange
        val pager = repository.getPlanetsPaged()

        // Act
        val flow = pager

        // Assert
        assertNotNull(flow)
    }

    @Test
    fun `when getPlanetDetail is called, it should fetch from API and cache the result`() =
        runTest(testScheduler) {
            // Arrange
            val uid = "123"
            val properties = PlanetPropertiesDto(
                name = "Tatooine",
                climate = "Arid",
                population = "200000",
                diameter = "1",
                gravity = "9",
                terrain = "terrain",
                url = "https://sahand.com",
                surfaceWater = "0",
                rotationPeriod = "365",
                orbitalPeriod = "180",
                created = "0-0-0",
                edited = "12-12-2024",
            )
            val resultDto = PlanetDetailResultDto(
                uid = "1",
                description = "The best planet",
                properties = properties
            )
            val response = PlanetDetailResponseDto(result = resultDto, message = "ok")

            coEvery { swApi.getPlanetDetails(uid) } returns response

            // Act
            val actual = repository.getPlanetDetail(uid)
            advanceUntilIdle()

            // Assert
            assertEquals(properties.toPlanetProperties(), actual)

            // Second call should hit cache
            val cached = repository.getPlanetDetail(uid)
            coVerify(exactly = 1) { swApi.getPlanetDetails(uid) }
            assertEquals(properties.toPlanetProperties(), cached)
        }
}
