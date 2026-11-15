package com.kush.mantis.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.ui.graphics.vector.ImageVector

/** The four calculators, in bottom bar order. */
enum class Tab(val icon: ImageVector, val label: String) {
    Basic(Icons.Filled.Calculate, "Basic calculator"),
    Scientific(Icons.Filled.Science, "Scientific calculator"),
    Programmer(Icons.Filled.Code, "Programmer calculator"),
    Converter(Icons.Filled.SwapVert, "Unit converter")
}
