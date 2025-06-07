package com.luzia.feature_planets_list.viewmodel

import app.cash.turbine.test
import com.luzia.core_domain.model.Failed
import com.luzia.core_domain.model.Loaded
import com.luzia.core_domain.model.PlanetProperties
import com.luzia.core_domain.usecase.GetPlanetDetailUseCase
import com.luzia.core_domain.usecase.GetPlanetsPagedFlowUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
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

class PlanetsListViewModelTest {
    private val getPlanetsPagedFlowUseCase: GetPlanetsPagedFlowUseCase = mockk()
    private val getPlanetDetailUseCase: GetPlanetDetailUseCase = mockk()
    private lateinit var viewModel: PlanetsListViewModel

    private val testScheduler = TestCoroutineScheduler()
    private val testDispatcher = StandardTestDispatcher(testScheduler)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        every { getPlanetsPagedFlowUseCase() } returns flowOf()
        viewModel = PlanetsListViewModel(
            getPlanetDetailUseCase,
            getPlanetsPagedFlowUseCase,
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `planetDetailsRequested fetches and updates state when not cached`() =
        runTest(testScheduler) {
            val uid = "123"
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
            coEvery { getPlanetDetailUseCase(uid) } returns detail
            viewModel.planetDetailsRequested(uid)

            viewModel.planetDetailsState.test {
                advanceUntilIdle()
                awaitItem()
                val result = awaitItem()
                assertEquals(mapOf(uid to Loaded(detail)), result)
            }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `planetDetailsRequested does nothing if planet already cached`() = runTest(testScheduler) {
        val uid = "123"
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
        coEvery { getPlanetDetailUseCase(any()) } returns detail

        viewModel.planetDetailsRequested(uid)
        advanceUntilIdle()

        viewModel.planetDetailsRequested(uid)
        advanceUntilIdle()

        coVerify(exactly = 1) { getPlanetDetailUseCase(uid) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `planetDetailsRequested catches exceptions and does not crash and set state to failed`() =
        runTest(testScheduler) {
            val uid = "errorPlanet"
            coEvery { getPlanetDetailUseCase(uid) } throws RuntimeException("API error")

            viewModel.planetDetailsRequested(uid)
            advanceUntilIdle()

            val current = viewModel.planetDetailsState.value
            assertTrue(current[uid] is Failed)
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `planetDetailsRequested emits Loading before Loaded`() = runTest(testScheduler) {
        val uid = "456"
        val detail = PlanetProperties(
            name = "Hoth",
            climate = "Frozen",
            population = "unknown",
            diameter = "7200",
            gravity = "1.1 standard",
            terrain = "tundra, ice caves, mountain ranges",
            url = "",
            surfaceWater = "100",
            rotationPeriod = "23",
            orbitalPeriod = "304",
            created = "",
            edited = ""
        )

        coEvery { getPlanetDetailUseCase(uid) } coAnswers {
            advanceUntilIdle() // simulate async delay
            detail
        }

        viewModel.planetDetailsRequested(uid)

        viewModel.planetDetailsState.test {
            val loadingState = awaitItem()[uid]
            assertTrue(loadingState is com.luzia.core_domain.model.Loading)

            val loadedState = awaitItem()[uid]
            assertTrue(loadedState is Loaded)
            assertEquals(detail, (loadedState as Loaded).data)
        }
    }

    @Test
    fun `planetDetailsState is empty by default`() {
        assertTrue(viewModel.planetDetailsState.value.isEmpty())
    }

}
