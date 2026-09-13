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
        Spacer(modifier = Modifier.height(16.dp))

        DisplayPanel(
            expression = expression,
            onExpressionChange = { viewModel.onEvent(ScientificEvent.OnExpressionChange(it)) },
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
            // Scientific Functions LazyRow
            androidx.compose.foundation.lazy.LazyRow(
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item { CalcButton("2nd", Modifier.width(72.dp).height(48.dp), color = if (isSecondMode) MantisGreen else MaterialTheme.colorScheme.surfaceVariant, textColor = if (isSecondMode) Color.Black else MaterialTheme.colorScheme.onSurface) { viewModel.onEvent(ScientificEvent.ToggleSecondMode) } }
                item { CalcButton(if (isDegreeMode) "DEG" else "RAD", Modifier.width(72.dp).height(48.dp)) { viewModel.onEvent(ScientificEvent.ToggleAngleMode) } }
                item { CalcButton(if (isSecondMode) "sin⁻¹" else "sin", Modifier.width(72.dp).height(48.dp)) { viewModel.onEvent(ScientificEvent.OnInput(if (isSecondMode) "asin(" else "sin(")) } }
                item { CalcButton(if (isSecondMode) "cos⁻¹" else "cos", Modifier.width(72.dp).height(48.dp)) { viewModel.onEvent(ScientificEvent.OnInput(if (isSecondMode) "acos(" else "cos(")) } }
                item { CalcButton(if (isSecondMode) "tan⁻¹" else "tan", Modifier.width(72.dp).height(48.dp)) { viewModel.onEvent(ScientificEvent.OnInput(if (isSecondMode) "atan(" else "tan(")) } }
                item { CalcButton("x^y", Modifier.width(72.dp).height(48.dp)) { viewModel.onEvent(ScientificEvent.OnInput("^")) } }
                item { CalcButton("√", Modifier.width(72.dp).height(48.dp)) { viewModel.onEvent(ScientificEvent.OnInput("√(")) } }
                item { CalcButton("ln", Modifier.width(72.dp).height(48.dp)) { viewModel.onEvent(ScientificEvent.OnInput("ln(")) } }
                item { CalcButton("log", Modifier.width(72.dp).height(48.dp)) { viewModel.onEvent(ScientificEvent.OnInput("log10(")) } }
                item { CalcButton("π", Modifier.width(72.dp).height(48.dp)) { viewModel.onEvent(ScientificEvent.OnInput("π")) } }
                item { CalcButton("e", Modifier.width(72.dp).height(48.dp)) { viewModel.onEvent(ScientificEvent.OnInput("e")) } }
            }

            val rowModifier = Modifier.weight(1f)

            // Row 1
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("7", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("7")) }
                CalcButton("8", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("8")) }
                CalcButton("9", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("9")) }
                CalcButton("(", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ScientificEvent.OnInput("(")) }
                CalcButton(")", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ScientificEvent.OnInput(")")) }
            }
            // Row 2
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("4", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("4")) }
                CalcButton("5", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("5")) }
                CalcButton("6", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("6")) }
                CalcButton("+", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ScientificEvent.OnInput("+")) }
                CalcButton("-", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ScientificEvent.OnInput("-")) }
            }
            // Row 3
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("1", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("1")) }
                CalcButton("2", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("2")) }
                CalcButton("3", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("3")) }
                CalcButton("÷", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ScientificEvent.OnInput("÷")) }
                CalcButton("×", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ScientificEvent.OnInput("×")) }
            }
            // Row 4
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("C", Modifier.weight(1f), textColor = AccentRed) { viewModel.onEvent(ScientificEvent.OnClear) }
                CalcButton("0", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput("0")) }
                CalcButton(".", Modifier.weight(1f)) { viewModel.onEvent(ScientificEvent.OnInput(".")) }
                CalcButton("⌫", Modifier.weight(1f), textColor = AccentOrange) { viewModel.onEvent(ScientificEvent.OnDelete) }
                CalcButton("=", Modifier.weight(1f), color = MantisGreen, textColor = Color.Black) { viewModel.onEvent(ScientificEvent.OnEquals) }
            }
        }
    }
}
