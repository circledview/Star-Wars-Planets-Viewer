package com.luzia.planetscodechallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.luzia.planetscodechallenge.navigation.NavigationGraph
import com.luzia.planetscodechallenge.ui.theme.PlanetsCodeChallengeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlanetsCodeChallengeTheme {
                NavigationGraph(Modifier.fillMaxSize())
            }
        }
    }
}
