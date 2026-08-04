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
import androidx.hilt.navigation.compose.hiltViewModel
import com.kush.mantis.core.ui.components.CalcButton
import com.kush.mantis.core.ui.components.DisplayPanel
import com.kush.mantis.ui.theme.AccentOrange
import com.kush.mantis.ui.theme.AccentRed
import com.kush.mantis.ui.theme.MantisGreen

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
            result = result,
            modifier = Modifier.weight(0.35f)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.65f)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val rowModifier = Modifier.weight(1f)

            // Scientific Row 1
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton(if (isSecondMode) "2nd" else "2nd", Modifier.weight(1f), color = if (isSecondMode) MantisGreen else MaterialTheme.colorScheme.surfaceVariant, textColor = if (isSecondMode) Color.Black else MaterialTheme.colorScheme.onSurface) { viewModel.onEvent(ScientificEvent.ToggleSecondMode) }
                CalcButton(if (isDegreeMode) "DEG" else "RAD", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.ToggleAngleMode) }
                CalcButton(if (isSecondMode) "sin⁻¹" else "sin", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput(if (isSecondMode) "sin⁻¹" else "sin")) }
                CalcButton(if (isSecondMode) "cos⁻¹" else "cos", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput(if (isSecondMode) "cos⁻¹" else "cos")) }
                CalcButton(if (isSecondMode) "tan⁻¹" else "tan", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput(if (isSecondMode) "tan⁻¹" else "tan")) }
            }

            // Scientific Row 2
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton(if (isSecondMode) "x³" else "x²", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput(if (isSecondMode) "x³" else "x²")) }
                CalcButton("xⁿ", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("xⁿ")) }
                CalcButton(if (isSecondMode) "³√" else "√", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput(if (isSecondMode) "³√" else "√")) }
                CalcButton(if (isSecondMode) "eˣ" else "ln", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput(if (isSecondMode) "eˣ" else "ln")) }
                CalcButton(if (isSecondMode) "10ˣ" else "log", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput(if (isSecondMode) "10ˣ" else "log")) }
            }

            // Row 3
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("(", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ScientificEvent.OnInput("(")) }
                CalcButton(")", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ScientificEvent.OnInput(")")) }
                CalcButton("π", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("π")) }
                CalcButton("e", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("e")) }
                CalcButton("C", Modifier.weight(1f), textColor = AccentRed) { viewModel.onEvent(ScientificEvent.OnClear) }
            }
            // Row 4
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("7", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("7")) }
                CalcButton("8", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("8")) }
                CalcButton("9", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("9")) }
                CalcButton("⌫", Modifier.weight(1f), textColor = AccentOrange) { viewModel.onEvent(ScientificEvent.OnDelete) }
                CalcButton("÷", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ScientificEvent.OnInput("÷")) }
            }
            // Row 5
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("4", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("4")) }
                CalcButton("5", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("5")) }
                CalcButton("6", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("6")) }
                CalcButton("×", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ScientificEvent.OnInput("×")) }
                CalcButton("-", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ScientificEvent.OnInput("-")) }
            }
            // Row 6
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("1", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("1")) }
                CalcButton("2", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("2")) }
                CalcButton("3", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("3")) }
                CalcButton("+", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ScientificEvent.OnInput("+")) }
                CalcButton("=", Modifier.weight(1f), color = MantisGreen, textColor = Color.Black) { viewModel.onEvent(ScientificEvent.OnEquals) }
            }
            // Row 7 (zero and dot)
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("0", Modifier.weight(2.05f)) { viewModel.onEvent(ScientificEvent.OnInput("0")) }
                CalcButton(".", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput(".")) }
                Spacer(Modifier.weight(2.05f))
            }
        }
    }
}
