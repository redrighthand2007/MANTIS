package com.kush.mantis.features.basic.presentation

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
fun BasicScreen(
    viewModel: BasicViewModel = hiltViewModel()
) {
    val expression by viewModel.expression.collectAsState()
    val result by viewModel.result.collectAsState()

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

        // Keypad
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.65f)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val rowModifier = Modifier.weight(1f)

            // Row 1
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("C", Modifier.weight(1f), textColor = AccentRed) { viewModel.onEvent(BasicCalcEvent.OnClearClick) }
                CalcButton("(", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(BasicCalcEvent.OnOperatorClick("(")) }
                CalcButton(")", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(BasicCalcEvent.OnOperatorClick(")")) }
                CalcButton("÷", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(BasicCalcEvent.OnOperatorClick("÷")) }
            }
            // Row 2
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("7", Modifier.weight(1f)) { viewModel.onEvent(BasicCalcEvent.OnNumberClick("7")) }
                CalcButton("8", Modifier.weight(1f)) { viewModel.onEvent(BasicCalcEvent.OnNumberClick("8")) }
                CalcButton("9", Modifier.weight(1f)) { viewModel.onEvent(BasicCalcEvent.OnNumberClick("9")) }
                CalcButton("×", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(BasicCalcEvent.OnOperatorClick("×")) }
            }
            // Row 3
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("4", Modifier.weight(1f)) { viewModel.onEvent(BasicCalcEvent.OnNumberClick("4")) }
                CalcButton("5", Modifier.weight(1f)) { viewModel.onEvent(BasicCalcEvent.OnNumberClick("5")) }
                CalcButton("6", Modifier.weight(1f)) { viewModel.onEvent(BasicCalcEvent.OnNumberClick("6")) }
                CalcButton("-", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(BasicCalcEvent.OnOperatorClick("-")) }
            }
            // Row 4
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("1", Modifier.weight(1f)) { viewModel.onEvent(BasicCalcEvent.OnNumberClick("1")) }
                CalcButton("2", Modifier.weight(1f)) { viewModel.onEvent(BasicCalcEvent.OnNumberClick("2")) }
                CalcButton("3", Modifier.weight(1f)) { viewModel.onEvent(BasicCalcEvent.OnNumberClick("3")) }
                CalcButton("+", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(BasicCalcEvent.OnOperatorClick("+")) }
            }
            // Row 5
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton(".", Modifier.weight(1f)) { viewModel.onEvent(BasicCalcEvent.OnNumberClick(".")) }
                CalcButton("0", Modifier.weight(1f)) { viewModel.onEvent(BasicCalcEvent.OnNumberClick("0")) }
                CalcButton("⌫", Modifier.weight(1f), textColor = AccentOrange) { viewModel.onEvent(BasicCalcEvent.OnDeleteClick) }
                CalcButton("=", Modifier.weight(1f), color = MantisGreen, textColor = Color.Black) { viewModel.onEvent(BasicCalcEvent.OnEqualsClick) }
            }
        }
    }
}
