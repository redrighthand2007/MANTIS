package com.kush.mantis.features.scientific.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.kush.mantis.core.ui.components.CalcButton
import com.kush.mantis.core.ui.components.DisplayPanel
import com.kush.mantis.ui.theme.AccentOrange
import com.kush.mantis.ui.theme.AccentRed
import com.kush.mantis.ui.theme.MantisGreen

import com.kush.mantis.core.ui.components.TopHeader

@Composable
fun ScientificScreen(
    viewModel: ScientificViewModel = hiltViewModel()
) {
    val expression by viewModel.expression.collectAsState()
    val result by viewModel.result.collectAsState()
    val isSecondMode by viewModel.isSecondMode.collectAsState()
    val isDegreeMode by viewModel.isDegreeMode.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        DisplayPanel(
            expression = expression,
            onExpressionChange = { viewModel.onEvent(ScientificEvent.OnExpressionChange(it)) },
            result = result,
            modifier = Modifier.weight(0.40f)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.60f)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val rowModifier = Modifier.weight(1f)

            // Row 1 (Scientific Functions 1); "2nd" swaps keys for their inverse/alternate function
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton(
                    "2nd",
                    Modifier.weight(1f),
                    color = if (isSecondMode) MantisGreen else MaterialTheme.colorScheme.surfaceVariant,
                    textColor = if (isSecondMode) Color.Black else MaterialTheme.colorScheme.onSurface,
                    contentDescription = if (isSecondMode) "Second functions on" else "Second functions off"
                ) { viewModel.onEvent(ScientificEvent.ToggleSecondMode) }
                FunctionKey(if (isSecondMode) "sin⁻¹" else "sin", viewModel)
                FunctionKey(if (isSecondMode) "cos⁻¹" else "cos", viewModel)
                FunctionKey(if (isSecondMode) "tan⁻¹" else "tan", viewModel)
                CalcButton(
                    if (isDegreeMode) "DEG" else "RAD",
                    Modifier.weight(1f),
                    contentDescription = if (isDegreeMode) "Degrees, switch to radians" else "Radians, switch to degrees"
                ) { viewModel.onEvent(ScientificEvent.ToggleAngleMode) }
            }

            // Row 2 (Scientific Functions 2)
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FunctionKey(if (isSecondMode) "n!" else "xʸ", viewModel)
                FunctionKey(if (isSecondMode) "³√" else "√", viewModel)
                FunctionKey(if (isSecondMode) "eˣ" else "ln", viewModel)
                FunctionKey(if (isSecondMode) "10ˣ" else "log", viewModel)
                FunctionKey(if (isSecondMode) "e" else "π", viewModel)
            }

            // Row 3
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("7", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("7")) }
                CalcButton("8", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("8")) }
                CalcButton("9", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("9")) }
                CalcButton("(", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ScientificEvent.OnInput("(")) }
                CalcButton(")", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ScientificEvent.OnInput(")")) }
            }
            // Row 4
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("4", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("4")) }
                CalcButton("5", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("5")) }
                CalcButton("6", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("6")) }
                CalcButton("+", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ScientificEvent.OnInput("+")) }
                CalcButton("-", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ScientificEvent.OnInput("-")) }
            }
            // Row 5
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("1", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("1")) }
                CalcButton("2", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("2")) }
                CalcButton("3", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("3")) }
                CalcButton("÷", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ScientificEvent.OnInput("÷")) }
                CalcButton("×", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ScientificEvent.OnInput("×")) }
            }
            // Row 6
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("C", Modifier.weight(1f), textColor = AccentRed, contentDescription = "Clear") { viewModel.onEvent(ScientificEvent.OnClear) }
                CalcButton("0", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("0")) }
                CalcButton(".", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput(".")) }
                CalcButton("⌫", Modifier.weight(1f), textColor = AccentOrange) { viewModel.onEvent(ScientificEvent.OnDelete) }
                CalcButton("=", Modifier.weight(1f), color = MantisGreen, textColor = Color.Black) { viewModel.onEvent(ScientificEvent.OnEquals) }
            }
        }
    }
}

// Sends the key label; ScientificViewModel maps it to the parser syntax via ScientificFunctions
@Composable
private fun RowScope.FunctionKey(label: String, viewModel: ScientificViewModel) {
    CalcButton(label, Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput(label)) }
}
