package com.kush.mantis.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kush.mantis.features.basic.presentation.BasicScreen
import com.kush.mantis.features.converter.presentation.ConverterScreen
import com.kush.mantis.features.programmer.presentation.ProgrammerScreen
import com.kush.mantis.features.scientific.presentation.ScientificScreen

/** Keeps keys a comfortable size on tablets and foldables instead of stretching edge to edge. */
private val MaxContentWidth = 600.dp
private const val SLIDE_DURATION_MS = 300

@Composable
fun MantisNavHost() {
    val navController = rememberNavController()

    Scaffold(bottomBar = { BottomNavBar(navController) }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.TopCenter
        ) {
            NavHost(
                navController = navController,
                startDestination = BasicRoute,
                modifier = Modifier.widthIn(max = MaxContentWidth),
                // Screens slide in the direction of their tab: moving right slides content left
                enterTransition = { slideInHorizontally(tween(SLIDE_DURATION_MS)) { width -> if (isMovingRight()) width else -width } },
                exitTransition = { slideOutHorizontally(tween(SLIDE_DURATION_MS)) { width -> if (isMovingRight()) -width else width } },
                popEnterTransition = { slideInHorizontally(tween(SLIDE_DURATION_MS)) { width -> if (isMovingRight()) width else -width } },
                popExitTransition = { slideOutHorizontally(tween(SLIDE_DURATION_MS)) { width -> if (isMovingRight()) -width else width } }
            ) {
                composable<BasicRoute> { BasicScreen() }
                composable<ScientificRoute> { ScientificScreen() }
                composable<ProgrammerRoute> { ProgrammerScreen() }
                composable<ConverterRoute> { ConverterScreen() }
            }
        }
    }
}

private fun AnimatedContentTransitionScope<NavBackStackEntry>.isMovingRight(): Boolean =
    tabIndexOf(targetState.destination.route) > tabIndexOf(initialState.destination.route)
