package com.luzia.feature_planets_list.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.luzia.core_domain.model.LoadableData
import com.luzia.core_domain.model.Loaded
import com.luzia.core_domain.model.PlanetProperties
import com.luzia.core_domain.model.PlanetSummary
import com.luzia.core_ui.animation.Shimmer
import com.luzia.core_ui.typography.AppTypography
import com.luzia.feature_planets_list.R
import com.luzia.feature_planets_list.viewmodel.PlanetsListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun PlanetsListScreen(
    onPlanetClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    val viewModel = koinViewModel<PlanetsListViewModel>()
    val pagingItems = viewModel.planetsPagingFlow.collectAsLazyPagingItems()
    val planetsDetails = viewModel.planetDetailsState.collectAsState().value

    Scaffold(modifier.fillMaxWidth()) { it ->
        PlanetsListContent(
            pagingItems,
            planetsDetails,
            onPlanetDetailsRequested = { viewModel.planetDetailsRequested(it) },
            onPlanetClick = onPlanetClick,
            modifier = Modifier.padding(it)

        )
    }


}

@Composable
fun PlanetsListContent(
    pagingItems: LazyPagingItems<PlanetSummary>,
    planetsDetails: Map<String, LoadableData<PlanetProperties>>,
    onPlanetDetailsRequested: (String) -> Unit,
    onPlanetClick: (String) -> Unit,
    modifier: Modifier
) {

    LazyColumn(modifier.fillMaxSize()) {
        items(pagingItems.itemCount) { index ->
            pagingItems[index]?.let { planet ->
                LaunchedEffect(planet.uid) {
                    onPlanetDetailsRequested(planet.uid)
                }
                val detail = planetsDetails[planet.uid]
                PlanetListItem(
                    name = planet.name,
                    detail = detail,
                    onClick = { onPlanetClick(planet.uid) }
                )
            }
        }

        when (pagingItems.loadState.append) {
            is LoadState.Loading -> item {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                }

            }

            is LoadState.Error -> item {
                Text(
                    stringResource(R.string.error_loading_more),
                    style = AppTypography.H2
                )
            }
            else -> {}
        }

        when (pagingItems.loadState.refresh) {
            is LoadState.Loading -> item {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                }
            }

            is LoadState.Error -> item {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Text(
                        stringResource(R.string.error_loading_initial_data),
                        style = AppTypography.H2
                    )
                }
            }

            else -> {}
        }
    }
}

@Composable
fun PlanetListItem(
    name: String,
    detail: LoadableData<PlanetProperties>?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {

    val contentDescription = buildString {
        append(stringResource(R.string.planet_name_formatted, name))
        append(
            stringResource(
                R.string.climate_formatted,
                if (detail is Loaded) detail.data.climate else stringResource(R.string.loading)
            )
        )
        append(
            stringResource(
                R.string.population_formatted,
                if (detail is Loaded) detail.data.population else stringResource(R.string.loading)
            )
        )
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(
                onClick = onClick,
                role = Role.Button
            )
            .semantics {
                this.contentDescription = contentDescription
            },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = name,
                style = AppTypography.H2
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.climate),
                    style = AppTypography.H3
                )

                Spacer(modifier = Modifier.width(8.dp))

                AnimatedVisibility(visible = detail is Loaded) {
                    Text(
                        text = detail?.data?.climate ?: "",
                        style = AppTypography.H3
                    )
                }

                AnimatedVisibility(visible = detail !is Loaded) {
                    Shimmer(width = 80.dp, height = 16.dp)
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.population),
                    style = AppTypography.H3
                )

                Spacer(modifier = Modifier.width(8.dp))

                AnimatedVisibility(visible = detail is Loaded) {
                    Text(
                        text = detail?.data?.population ?: "",
                        style = AppTypography.H3
                    )
                }

                AnimatedVisibility(visible = detail !is Loaded) {
                    Shimmer(width = 60.dp, height = 16.dp)
                }
            }
        }
    }
}
