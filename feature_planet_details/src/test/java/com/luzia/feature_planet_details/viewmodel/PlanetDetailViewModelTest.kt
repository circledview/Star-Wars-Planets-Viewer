package com.luzia.feature_planet_details.viewmodel

import app.cash.turbine.test
import com.luzia.core_domain.model.*
import com.luzia.core_domain.repository.PlanetsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PlanetDetailViewModelTest {

    private val repository: PlanetsRepository = mockk()
    private val testScheduler = TestCoroutineScheduler()
    private val testDispatcher = StandardTestDispatcher(testScheduler)

    private val uid = "planet-123"
    private val detail = PlanetProperties(
        name = "Alderaan",
        climate = "temperate",
        population = "2 billion",
        diameter = "12500",
        gravity = "1 standard",
        terrain = "grasslands, mountains",
        url = "https://swapi.dev/api/planets/2/",
        surfaceWater = "40",
        rotationPeriod = "24",
        orbitalPeriod = "364",
        created = "2023-01-01",
        edited = "2024-01-01"
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init fetches planet detail and emits Loading then Loaded`() = runTest(testScheduler) {
        coEvery { repository.getPlanetDetail(uid) } returns detail

        val viewModel = PlanetDetailViewModel(repository, uid)

        viewModel.state.test {
            assertTrue(awaitItem() is Loading)
            advanceUntilIdle()
            assertEquals(Loaded(detail), awaitItem())
        }
    }

    @Test
    fun `init handles exception and emits Failed`() = runTest(testScheduler) {
        val exception = RuntimeException("API error")
        coEvery { repository.getPlanetDetail(uid) } throws exception

        val viewModel = PlanetDetailViewModel(repository, uid)

        viewModel.state.test {
            assertTrue(awaitItem() is Loading)
            advanceUntilIdle()
            val failedState = awaitItem()
            assertTrue(failedState is Failed)
            assertEquals(exception, (failedState as Failed).throwble)
        }
    }

    @Test
    fun `retry fetches again after failure`() = runTest(testScheduler) {
        var callCount = 0
        coEvery { repository.getPlanetDetail(uid) } answers {
            callCount++
            if (callCount == 1) throw RuntimeException("first fail")
            detail
        }

        val viewModel = PlanetDetailViewModel(repository, uid)

        viewModel.state.test {
            assertTrue(awaitItem() is Loading)
            advanceUntilIdle()
            assertTrue(awaitItem() is Failed)

            viewModel.retry()
            assertTrue(awaitItem() is Loading)
            advanceUntilIdle()
            assertEquals(Loaded(detail), awaitItem())
        }
    }

    @Test
    fun `initial state is Loading immediately in init`() = runTest(testScheduler) {
        coEvery { repository.getPlanetDetail(uid) } returns detail

        val viewModel = PlanetDetailViewModel(repository, uid)
        assertTrue(viewModel.state.value is Loading)
    }
}
