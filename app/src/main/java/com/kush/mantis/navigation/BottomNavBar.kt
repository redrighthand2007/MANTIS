package com.kush.mantis.navigation

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kush.mantis.ui.theme.MantisGreen

import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType

@Composable
fun BottomNavBar(navController: NavController, isHapticEnabled: Boolean = true) {
    val items = listOf(
        Pair("Basic", BasicRoute),
        Pair("Sci", ScientificRoute),
        Pair("Prog", ProgrammerRoute),
        Pair("Conv", ConverterRoute),
        Pair("Hist", HistoryRoute),
        Pair("Set", SettingsRoute)
    )

    val haptic = LocalHapticFeedback.current

    NavigationBar {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry.value?.destination?.route

        items.forEach { (label, routeObj) ->
            val routeString = routeObj::class.qualifiedName ?: ""
            val selected = currentRoute == routeString

            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (isHapticEnabled && !selected) {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    }
                    navController.navigate(routeObj) {
                        popUpTo(BasicRoute) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {  },
                label = { Text(label, color = if (selected) MantisGreen else androidx.compose.ui.graphics.Color.Gray) }
            )
        }
    }
}
