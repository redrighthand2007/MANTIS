package com.kush.mantis.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kush.mantis.features.basic.presentation.BasicScreen
import com.kush.mantis.features.converter.presentation.ConverterScreen
import com.kush.mantis.features.programmer.presentation.ProgrammerScreen
import com.kush.mantis.features.scientific.presentation.ScientificScreen

/** Keeps keys a comfortable size on tablets and foldables instead of stretching edge to edge. */
private val MaxContentWidth = 600.dp

// A quick crossfade between calculators: the old screen fades out slightly faster than the new one fades in
private const val FADE_IN_MS = 650
private const val FADE_OUT_MS = 350

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
                enterTransition = { fadeIn(tween(FADE_IN_MS)) },
                exitTransition = { fadeOut(tween(FADE_OUT_MS)) },
                popEnterTransition = { fadeIn(tween(FADE_IN_MS)) },
                popExitTransition = { fadeOut(tween(FADE_OUT_MS)) }
            ) {
                composable<BasicRoute> { BasicScreen() }
                composable<ScientificRoute> { ScientificScreen() }
                composable<ProgrammerRoute> { ProgrammerScreen() }
                composable<ConverterRoute> { ConverterScreen() }
            }
        }
    }
}
