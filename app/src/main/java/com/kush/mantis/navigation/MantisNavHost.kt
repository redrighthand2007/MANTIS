package com.kush.mantis.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kush.mantis.features.basic.presentation.BasicScreen
import com.kush.mantis.features.converter.presentation.ConverterScreen
import com.kush.mantis.features.programmer.presentation.ProgrammerScreen
import com.kush.mantis.features.scientific.presentation.ScientificScreen

/** Keeps keys a comfortable size on tablets and foldables instead of stretching edge to edge. */
private val MaxContentWidth = 600.dp

// The only screen transition in the app: a plain crossfade between calculators, same duration both ways
private const val FADE_MS = 500

/**
 * Hosts the four calculators behind the bottom bar.
 *
 * Tabs are a simple selected state with a Crossfade rather than a navigation back stack, so rapid
 * switching just retargets the fade instead of interrupting pop/restore operations. Each screen's
 * ViewModel is activity-scoped, and its UI state is kept by the saveable state holder.
 */
@Composable
fun MantisNavHost() {
    var selectedTab by rememberSaveable { mutableStateOf(Tab.Basic) }
    val screenStates = rememberSaveableStateHolder()

    // Back from another calculator returns to Basic; Back on Basic leaves the app
    BackHandler(enabled = selectedTab != Tab.Basic) { selectedTab = Tab.Basic }

    Scaffold(
        bottomBar = { BottomNavBar(selectedTab = selectedTab, onTabSelected = { selectedTab = it }) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.TopCenter
        ) {
            Crossfade(
                targetState = selectedTab,
                animationSpec = tween(FADE_MS),
                modifier = Modifier.widthIn(max = MaxContentWidth),
                label = "calculatorTabs"
            ) { tab ->
                screenStates.SaveableStateProvider(tab.name) {
                    when (tab) {
                        Tab.Basic -> BasicScreen()
                        Tab.Scientific -> ScientificScreen()
                        Tab.Programmer -> ProgrammerScreen()
                        Tab.Converter -> ConverterScreen()
                    }
                }
            }
        }
    }
}
