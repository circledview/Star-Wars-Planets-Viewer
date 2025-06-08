package com.luzia.planetscodechallenge

import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.luzia.core_domain.model.PlanetProperties
import com.luzia.core_domain.model.PlanetSummary
import com.luzia.core_domain.usecase.GetPlanetDetailUseCase
import com.luzia.core_domain.usecase.GetPlanetsPagedFlowUseCase
import com.luzia.feature_planet_details.viewmodel.PlanetDetailViewModel
import com.luzia.feature_planets_list.viewmodel.PlanetsListViewModel
import com.luzia.planetscodechallenge.PlanetsCodeChallengeApp.Companion.applicationDIModules
import com.luzia.planetscodechallenge.navigation.AppNavigationGraph
import com.luzia.planetscodechallenge.utils.KoinTestRule
import com.luzia.planetscodechallenge.utils.TestPagingSource
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.koin.dsl.module

class UITests {
    private val getPlanetDetailUseCase: GetPlanetDetailUseCase = mockk()
    private val getPlanetsPagedFlowUseCase: GetPlanetsPagedFlowUseCase = mockk()
    private val instrumentedTestModule = module {
        factory<PlanetDetailViewModel> { PlanetDetailViewModel(getPlanetDetailUseCase, "1") }
        factory<PlanetsListViewModel> {
            PlanetsListViewModel(
                getPlanetDetailUseCase,
                getPlanetsPagedFlowUseCase,
            )
        }
        factory<PlanetDetailViewModel> {
            PlanetDetailViewModel(
                getPlanetDetailUseCase, "1"
            )
        }
    }

    private val koinTestRule = KoinTestRule(
        modules = applicationDIModules + instrumentedTestModule
    )

    private val composeRule = createComposeRule()

    @get:Rule
    val ruleChain: RuleChain = RuleChain.outerRule(koinTestRule).around(composeRule)

    @Test
    fun shouldShowProperList() = runTest {
        val testItems = buildList {
            repeat(10) { add(PlanetSummary("$it", "Planet $it", "https://luzia.com")) }
        }

        val pagingSourceFactory = { TestPagingSource(testItems) }

        val responseFlow = Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow

        every { getPlanetsPagedFlowUseCase() } returns responseFlow

        composeRule.setContent {
            AppNavigationGraph()
        }

        composeRule.waitUntil(5000) {
            composeRule.onNodeWithText("Planet 1").isDisplayed()
        }

        composeRule.onNodeWithText("Planet 0").assertExists()
        composeRule.onNodeWithText("Planet 5").assertExists()
    }


    @Test
    fun shouldShowPlanetDetailsAfterClickOnItem() = runTest {
        val testItems = buildList {
            repeat(10) { add(PlanetSummary("$it", "Planet $it", "https://luzia.com")) }
        }

        val pagingSourceFactory = { TestPagingSource(testItems) }

        val responseFlow = Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow

        every { getPlanetsPagedFlowUseCase() } returns responseFlow

        coEvery { getPlanetDetailUseCase(any()) } coAnswers {
            PlanetProperties(
                name = "Planet 1",
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
        }

        composeRule.setContent {
            AppNavigationGraph()
        }

        composeRule.waitUntil(5000) {
            composeRule.onNodeWithText("Planet 1").isDisplayed()
        }

        composeRule.onNodeWithText("Planet 1").performClick()

        composeRule.waitUntil(5000) {
            composeRule.onNodeWithText("Planet 1").isDisplayed()
        }
        composeRule.onNodeWithText("Arid").assertExists()
        composeRule.onNodeWithText("200000").assertExists()
        composeRule.onNodeWithText("1 standard").assertExists()
        composeRule.onNodeWithText("desert").assertExists()

    }
}