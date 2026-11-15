package com.kush.mantis.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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

// A short slide plus a crossfade feels lighter than pushing whole screens across the display
private const val TRANSITION_MS = 260
private const val FADE_OUT_MS = 160
private const val SLIDE_FRACTION = 4

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
                enterTransition = { tabEnter() },
                exitTransition = { tabExit() },
                popEnterTransition = { tabEnter() },
                popExitTransition = { tabExit() }
            ) {
                composable<BasicRoute> { BasicScreen() }
                composable<ScientificRoute> { ScientificScreen() }
                composable<ProgrammerRoute> { ProgrammerScreen() }
                composable<ConverterRoute> { ConverterScreen() }
            }
        }
    }
}

private fun AnimatedContentTransitionScope<NavBackStackEntry>.tabEnter(): EnterTransition {
    val direction = if (isMovingRight()) 1 else -1
    return slideInHorizontally(tween(TRANSITION_MS, easing = FastOutSlowInEasing)) { width -> direction * width / SLIDE_FRACTION } +
        fadeIn(tween(TRANSITION_MS, easing = LinearOutSlowInEasing))
}

private fun AnimatedContentTransitionScope<NavBackStackEntry>.tabExit(): ExitTransition {
    val direction = if (isMovingRight()) -1 else 1
    return slideOutHorizontally(tween(TRANSITION_MS, easing = FastOutSlowInEasing)) { width -> direction * width / SLIDE_FRACTION } +
        fadeOut(tween(FADE_OUT_MS, easing = FastOutLinearInEasing))
}

private fun AnimatedContentTransitionScope<NavBackStackEntry>.isMovingRight(): Boolean =
    tabIndexOf(targetState.destination.route) > tabIndexOf(initialState.destination.route)
