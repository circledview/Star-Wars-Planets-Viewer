package com.luzia.feature_planet_details.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.luzia.core_domain.model.Failed
import com.luzia.core_domain.model.LoadableData
import com.luzia.core_domain.model.Loaded
import com.luzia.core_domain.model.Loading
import com.luzia.core_domain.model.NotLoaded
import com.luzia.core_domain.model.PlanetProperties
import com.luzia.core_ui.animation.Shimmer
import com.luzia.core_ui.typography.AppTypography
import com.luzia.feature_planet_details.R
import com.luzia.feature_planet_details.viewmodel.PlanetDetailViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun PlanetDetailScreen(
    uid: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel = koinViewModel<PlanetDetailViewModel>() {
        parametersOf(uid)
    }
    val state by viewModel.state.collectAsState()

    PlanetDetailContent(
        state = state,
        onBackClick = onBackClick,
        onRetry = { viewModel.retry() },
        modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanetDetailContent(
    state: LoadableData<PlanetProperties>,
    onBackClick: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.planet_details),
                        style = AppTypography.H2
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            AnimatedVisibility(visible = state is NotLoaded || state is Loading) {
                ShimmerPlanetDetails()
            }

            AnimatedVisibility(visible = state is Failed) {
                val error = state as Failed
                ErrorState(
                    message = error.title ?: "",
                    onRetry = onRetry
                )
            }

            AnimatedVisibility(visible = state is Loaded) {
                PlanetDetailContent((state as Loaded).data)
            }
        }
    }
}

@Composable
private fun ErrorState(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.error, message),
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun PlanetDetailContent(
    planet: PlanetProperties,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        Text(text = planet.name, style = AppTypography.H1)

        Spacer(modifier = Modifier.height(16.dp))

        DetailRow(label = stringResource(R.string.climate), value = planet.climate)
        DetailRow(label = stringResource(R.string.population), value = planet.population)
        DetailRow(label = stringResource(R.string.diameter), value = planet.diameter)
        DetailRow(label = stringResource(R.string.gravity), value = planet.gravity)
        DetailRow(label = stringResource(R.string.terrain), value = planet.terrain)

        Spacer(modifier = Modifier.height(24.dp))

    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = "$label: ", style = AppTypography.H3)
        Text(text = value, style = AppTypography.H3)
    }
}

@Composable
fun ShimmerPlanetDetails() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        Shimmer(width = 180.dp, height = 28.dp)
        Spacer(modifier = Modifier.height(24.dp))

        repeat(5) {
            ShimmerRow()
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ShimmerRow() {
    Row {
        Shimmer(width = 100.dp, height = 20.dp)
        Spacer(modifier = Modifier.weight(1f))
        Shimmer(width = 80.dp, height = 20.dp)
    }
}