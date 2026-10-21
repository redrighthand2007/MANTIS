package com.kush.mantis.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kush.mantis.ui.theme.MantisGreen

@Composable
fun BottomNavBar(navController: NavController, isHapticEnabled: Boolean = true) {
    val items = listOf(
        Triple(Icons.Filled.Calculate, BasicRoute, "Basic calculator"),
        Triple(Icons.Filled.Science, ScientificRoute, "Scientific calculator"),
        Triple(Icons.Filled.Code, ProgrammerRoute, "Programmer calculator"),
        Triple(Icons.Filled.SwapVert, ConverterRoute, "Unit converter")
    )

    val view = androidx.compose.ui.platform.LocalView.current

    // Box to provide the padding and background transparency around the floating bar
    Box(
        modifier = Modifier
            .fillMaxWidth()
            // Keep the floating bar above the gesture handle / 3-button navigation bar
            .navigationBarsPadding()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.surfaceVariant,
            shadowElevation = 8.dp
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val navBackStackEntry = navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry.value?.destination?.route

                items.forEach { (iconVec, routeObj, label) ->
                    val routeString = routeObj::class.qualifiedName ?: ""
                    val selected = currentRoute == routeString

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null, // Custom clean look (no default square ripple)
                                role = androidx.compose.ui.semantics.Role.Tab,
                                onClick = {
                                    if (isHapticEnabled && !selected) {
                                        view.performHapticFeedback(android.view.HapticFeedbackConstants.KEYBOARD_TAP)
                                    }
                                    navController.navigate(routeObj) {
                                        popUpTo(BasicRoute) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = iconVec,
                            contentDescription = label,
                            modifier = Modifier.size(24.dp),
                            tint = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                        )
                    }
                }
            }
        }
    }
}
