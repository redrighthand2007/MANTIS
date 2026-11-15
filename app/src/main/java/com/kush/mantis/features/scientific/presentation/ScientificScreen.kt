package com.kush.mantis.features.scientific.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.kush.mantis.core.ui.components.CalculatorLayout
import com.kush.mantis.core.ui.components.ClearKey
import com.kush.mantis.core.ui.components.DeleteKey
import com.kush.mantis.core.ui.components.DisplayPanel
import com.kush.mantis.core.ui.components.EqualsKey
import com.kush.mantis.core.ui.components.Key
import com.kush.mantis.core.ui.components.KeypadRow
import com.kush.mantis.core.ui.components.OperatorKey
import com.kush.mantis.core.ui.components.ToggleKey

@Composable
fun ScientificScreen(viewModel: ScientificViewModel = hiltViewModel()) {
    val expression by viewModel.expression.collectAsState()
    val result by viewModel.result.collectAsState()
    val isSecondMode by viewModel.isSecondMode.collectAsState()
    val isDegreeMode by viewModel.isDegreeMode.collectAsState()

    // Keys send their label; ScientificViewModel maps it to parser syntax via ScientificFunctions
    val input = { label: String -> viewModel.onEvent(ScientificEvent.OnInput(label)) }
    // "2nd" swaps each function key for its inverse or alternate
    val pick = { primary: String, second: String -> if (isSecondMode) second else primary }

    CalculatorLayout(
        display = { modifier ->
            DisplayPanel(
                expression = expression,
                onExpressionChange = { viewModel.onEvent(ScientificEvent.OnExpressionChange(it)) },
                result = result,
                modifier = modifier
            )
        }
    ) {
        KeypadRow {
            ToggleKey(
                symbol = "2nd",
                active = isSecondMode,
                contentDescription = if (isSecondMode) "Second functions on" else "Second functions off"
            ) { viewModel.onEvent(ScientificEvent.ToggleSecondMode) }
            listOf(pick("sin", "sin⁻¹"), pick("cos", "cos⁻¹"), pick("tan", "tan⁻¹")).forEach { Key(it) { input(it) } }
            Key(
                symbol = if (isDegreeMode) "DEG" else "RAD",
                contentDescription = if (isDegreeMode) "Degrees, switch to radians" else "Radians, switch to degrees"
            ) { viewModel.onEvent(ScientificEvent.ToggleAngleMode) }
        }
        KeypadRow {
            listOf(pick("xʸ", "n!"), pick("√", "³√"), pick("ln", "eˣ"), pick("log", "10ˣ"), pick("π", "e"))
                .forEach { Key(it) { input(it) } }
        }
        KeypadRow {
            listOf("7", "8", "9").forEach { Key(it) { input(it) } }
            listOf("(", ")").forEach { OperatorKey(it) { input(it) } }
        }
        KeypadRow {
            listOf("4", "5", "6").forEach { Key(it) { input(it) } }
            listOf("+", "-").forEach { OperatorKey(it) { input(it) } }
        }
        KeypadRow {
            listOf("1", "2", "3").forEach { Key(it) { input(it) } }
            listOf("÷", "×").forEach { OperatorKey(it) { input(it) } }
        }
        KeypadRow {
            ClearKey { viewModel.onEvent(ScientificEvent.OnClear) }
            Key("0") { input("0") }
            Key(".") { input(".") }
            DeleteKey { viewModel.onEvent(ScientificEvent.OnDelete) }
            EqualsKey { viewModel.onEvent(ScientificEvent.OnEquals) }
        }
    }
}
