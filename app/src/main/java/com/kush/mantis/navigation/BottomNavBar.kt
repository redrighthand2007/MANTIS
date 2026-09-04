package com.kush.mantis.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kush.mantis.ui.theme.MantisGreen
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType

@Composable
fun BottomNavBar(navController: NavController, isHapticEnabled: Boolean = true) {
    val items = listOf(
        Pair(Icons.Filled.Calculate, BasicRoute),
        Pair(Icons.Filled.Science, ScientificRoute),
        Pair(Icons.Filled.Code, ProgrammerRoute),
        Pair(Icons.Filled.SwapVert, ConverterRoute),
        Pair(Icons.Filled.History, HistoryRoute),
        Pair(Icons.Filled.Settings, SettingsRoute)
    )

    val haptic = LocalHapticFeedback.current
    val view = androidx.compose.ui.platform.LocalView.current

    NavigationBar(
        containerColor = androidx.compose.material3.MaterialTheme.colorScheme.background,
        contentColor = Color.Gray
    ) {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry.value?.destination?.route

        items.forEach { (iconVec, routeObj) ->
            val routeString = routeObj::class.qualifiedName ?: ""
            val selected = currentRoute == routeString

            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (isHapticEnabled && !selected) {
                        view.performHapticFeedback(android.view.HapticFeedbackConstants.KEYBOARD_TAP)
                    }
                    navController.navigate(routeObj) {
                        popUpTo(BasicRoute) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { 
                    Icon(
                        imageVector = iconVec,
                        contentDescription = routeString,
                        tint = if (selected) MantisGreen else Color.Gray
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent,
                    selectedIconColor = MantisGreen,
                    unselectedIconColor = Color.Gray
                )
            )
        }
    }
}
