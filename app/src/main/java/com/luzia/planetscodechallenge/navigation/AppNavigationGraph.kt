package com.luzia.planetscodechallenge.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.luzia.feature_planet_details.ui.PlanetDetailScreen
import com.luzia.feature_planets_list.ui.PlanetsListScreen
import kotlinx.serialization.Serializable

@Serializable
object PlanetsList

@Serializable
data class PlanetDetails(val uid: String)

@Composable
fun AppNavigationGraph(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = PlanetsList, modifier = modifier) {
        composable<PlanetsList> {
            PlanetsListScreen(onPlanetClick = {
                navController.navigate(PlanetDetails(it))
            })
        }
        composable<PlanetDetails> { backStackEntry ->
            val profile: PlanetDetails = backStackEntry.toRoute()
            PlanetDetailScreen(uid = profile.uid, onBackClick = {
                navController.popBackStack()
            })
        }
    }

}

