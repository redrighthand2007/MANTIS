package com.kush.mantis.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kush.mantis.features.basic.presentation.BasicScreen
import com.kush.mantis.features.scientific.presentation.ScientificScreen
import com.kush.mantis.features.programmer.presentation.ProgrammerScreen
import com.kush.mantis.features.converter.presentation.ConverterScreen
import com.kush.mantis.features.history.presentation.HistoryScreen
import com.kush.mantis.features.settings.presentation.SettingsScreen

@Composable
fun MantisNavHost(isHapticEnabled: Boolean = true) {
    val navController = rememberNavController()

    val routeOrder = listOf(
        BasicRoute::class.qualifiedName,
        ScientificRoute::class.qualifiedName,
        ProgrammerRoute::class.qualifiedName,
        ConverterRoute::class.qualifiedName,
        HistoryRoute::class.qualifiedName,
        SettingsRoute::class.qualifiedName
    )

    Scaffold(
        bottomBar = { BottomNavBar(navController, isHapticEnabled) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BasicRoute,
            modifier = Modifier.padding(innerPadding),
            enterTransition = {
                val initialIndex = routeOrder.indexOf(initialState.destination.route?.substringBefore("?"))
                val targetIndex = routeOrder.indexOf(targetState.destination.route?.substringBefore("?"))
                val isLeftToRight = targetIndex > initialIndex
                androidx.compose.animation.slideInHorizontally(
                    initialOffsetX = { if (isLeftToRight) it else -it },
                    animationSpec = androidx.compose.animation.core.tween(300)
                )
            },
            exitTransition = {
                val initialIndex = routeOrder.indexOf(initialState.destination.route?.substringBefore("?"))
                val targetIndex = routeOrder.indexOf(targetState.destination.route?.substringBefore("?"))
                val isLeftToRight = targetIndex > initialIndex
                androidx.compose.animation.slideOutHorizontally(
                    targetOffsetX = { if (isLeftToRight) -it else it },
                    animationSpec = androidx.compose.animation.core.tween(300)
                )
            },
            popEnterTransition = {
                val initialIndex = routeOrder.indexOf(initialState.destination.route?.substringBefore("?"))
                val targetIndex = routeOrder.indexOf(targetState.destination.route?.substringBefore("?"))
                val isLeftToRight = targetIndex > initialIndex
                androidx.compose.animation.slideInHorizontally(
                    initialOffsetX = { if (isLeftToRight) it else -it },
                    animationSpec = androidx.compose.animation.core.tween(300)
                )
            },
            popExitTransition = {
                val initialIndex = routeOrder.indexOf(initialState.destination.route?.substringBefore("?"))
                val targetIndex = routeOrder.indexOf(targetState.destination.route?.substringBefore("?"))
                val isLeftToRight = targetIndex > initialIndex
                androidx.compose.animation.slideOutHorizontally(
                    targetOffsetX = { if (isLeftToRight) -it else it },
                    animationSpec = androidx.compose.animation.core.tween(300)
                )
            }
        ) {
            composable<BasicRoute> { BasicScreen() }
            composable<ScientificRoute> { ScientificScreen() }
            composable<ProgrammerRoute> { ProgrammerScreen() }
            composable<ConverterRoute> { ConverterScreen() }
            composable<HistoryRoute> { HistoryScreen() }
            composable<SettingsRoute> { SettingsScreen() }
        }
    }
}
