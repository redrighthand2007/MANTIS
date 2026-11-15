package com.kush.mantis.core.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp

private val CompactHeightThreshold = 600.dp

/** True in short windows such as split-screen, where screens drop decoration to keep keys usable. */
@Composable
fun isCompactHeight(): Boolean {
    val windowHeight = with(LocalDensity.current) { LocalWindowInfo.current.containerSize.height.toDp() }
    return windowHeight < CompactHeightThreshold
}
