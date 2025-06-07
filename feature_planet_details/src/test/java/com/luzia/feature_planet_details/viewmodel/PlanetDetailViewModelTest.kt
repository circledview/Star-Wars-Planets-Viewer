package com.luzia.feature_planet_details.viewmodel

import app.cash.turbine.test
import com.luzia.core_domain.model.Failed
import com.luzia.core_domain.model.Loaded
import com.luzia.core_domain.model.Loading
import com.luzia.core_domain.model.PlanetProperties
import com.luzia.core_domain.usecase.GetPlanetDetailUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PlanetDetailViewModelTest {

    private val getPlanetDetailUseCase: GetPlanetDetailUseCase = mockk()
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
        coEvery { getPlanetDetailUseCase(uid) } returns detail

        val viewModel = PlanetDetailViewModel(getPlanetDetailUseCase, uid)

        viewModel.state.test {
            assertTrue(awaitItem() is Loading)
            advanceUntilIdle()
            assertEquals(Loaded(detail), awaitItem())
        }
    }

    @Test
    fun `init handles exception and emits Failed`() = runTest(testScheduler) {
        val exception = RuntimeException("API error")
        coEvery { getPlanetDetailUseCase(uid) } throws exception

        val viewModel = PlanetDetailViewModel(getPlanetDetailUseCase, uid)

        viewModel.state.test {
            assertTrue(awaitItem() is Loading)
            advanceUntilIdle()
            val failedState = awaitItem()
            assertTrue(failedState is Failed)
            assertEquals(exception, (failedState as Failed).throwable)
        }
    }

    @Test
    fun `retry fetches again after failure`() = runTest(testScheduler) {
        var callCount = 0
        coEvery { getPlanetDetailUseCase(uid) } answers {
            callCount++
            if (callCount == 1) throw RuntimeException("first fail")
            detail
        }

        val viewModel = PlanetDetailViewModel(getPlanetDetailUseCase, uid)

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
        coEvery { getPlanetDetailUseCase(uid) } returns detail

        val viewModel = PlanetDetailViewModel(getPlanetDetailUseCase, uid)
        assertTrue(viewModel.state.value is Loading)
    }
}
