package com.kush.mantis.core.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

/** True in short windows such as split-screen, where screens drop decoration to keep keys usable. */
@Composable
fun isCompactHeight(): Boolean = LocalConfiguration.current.screenHeightDp < 600
