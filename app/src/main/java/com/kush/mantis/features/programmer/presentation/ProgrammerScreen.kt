package com.kush.mantis.features.programmer.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.kush.mantis.core.ui.components.CalcButton
import com.kush.mantis.core.ui.components.TopHeader
import com.kush.mantis.core.ui.components.DisplayPanel
import com.kush.mantis.features.programmer.domain.BaseConverter
import com.kush.mantis.ui.theme.AccentOrange
import com.kush.mantis.ui.theme.AccentRed
import com.kush.mantis.ui.theme.MantisGreen

@Composable
fun ProgrammerScreen(
    viewModel: ProgrammerViewModel = hiltViewModel()
) {
    val currentValue by viewModel.currentValue.collectAsState()
    val activeBase by viewModel.activeBase.collectAsState()
    val inputString by viewModel.inputString.collectAsState()

    val expression by viewModel.expression.collectAsState()
    val result by viewModel.result.collectAsState()

    var baseExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        DisplayPanel(
            expression = expression,
            onExpressionChange = { viewModel.onEvent(ProgrammerEvent.OnExpressionChange(it)) },
            result = result,
            readOnly = true,
            modifier = Modifier.weight(0.40f)
        )
        // 6 Equal Rows (1: PillSelector, 2: Hex, 3-6: Keypad)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.60f)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val rowModifier = Modifier.weight(1f)
            
            // Row 1: PillSelector
            Box(modifier = rowModifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                com.kush.mantis.core.ui.components.PillSelector(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                    items = listOf("HEX", "DEC", "OCT", "BIN"),
                    selectedItem = when (activeBase) {
                        16 -> "HEX"
                        10 -> "DEC"
                        8 -> "OCT"
                        2 -> "BIN"
                        else -> "DEC"
                    },
                    onItemSelected = { selected ->
                        val base = when (selected) {
                            "HEX" -> 16
                            "DEC" -> 10
                            "OCT" -> 8
                            "BIN" -> 2
                            else -> 10
                        }
                        viewModel.onEvent(ProgrammerEvent.SetBase(base))
                    }
                )
            }

            // Row 2: Hex Letters
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DigitKey("A", activeBase, viewModel)
                DigitKey("B", activeBase, viewModel)
                DigitKey("C", activeBase, viewModel)
                DigitKey("D", activeBase, viewModel)
                DigitKey("E", activeBase, viewModel)
                DigitKey("F", activeBase, viewModel)
            }
            
            // Row 3
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DigitKey("7", activeBase, viewModel)
                DigitKey("8", activeBase, viewModel)
                DigitKey("9", activeBase, viewModel)
                CalcButton("<<", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ProgrammerEvent.OnBitwiseOp("<<")) }
                CalcButton(">>", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ProgrammerEvent.OnBitwiseOp(">>")) }
            }
            
            // Row 4
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DigitKey("4", activeBase, viewModel)
                DigitKey("5", activeBase, viewModel)
                DigitKey("6", activeBase, viewModel)
                CalcButton("XOR", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ProgrammerEvent.OnBitwiseOp("XOR")) }
                CalcButton("NOT", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ProgrammerEvent.OnBitwiseOp("NOT")) }
            }
            
            // Row 5
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DigitKey("1", activeBase, viewModel)
                DigitKey("2", activeBase, viewModel)
                DigitKey("3", activeBase, viewModel)
                CalcButton("AND", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ProgrammerEvent.OnBitwiseOp("AND")) }
                CalcButton("OR", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ProgrammerEvent.OnBitwiseOp("OR")) }
            }
            
            // Row 6
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("C", Modifier.weight(1f), textColor = AccentRed, contentDescription = "Clear") { viewModel.onEvent(ProgrammerEvent.OnClear) }
                DigitKey("0", activeBase, viewModel)
                CalcButton(".", Modifier.weight(1f), enabled = false) {}
                CalcButton("⌫", Modifier.weight(1f), textColor = AccentOrange) { viewModel.onEvent(ProgrammerEvent.OnDelete) }
                CalcButton("=", Modifier.weight(1f), color = MantisGreen, textColor = Color.Black) { viewModel.onEvent(ProgrammerEvent.OnEquals) }
            }
        }
    }
}

// Digits that are not valid in the active base (e.g. 2-9 in BIN) are shown disabled
@Composable
private fun RowScope.DigitKey(digit: String, activeBase: Int, viewModel: ProgrammerViewModel) {
    CalcButton(
        digit,
        Modifier.weight(1f),
        enabled = BaseConverter.isValidDigit(digit[0], activeBase)
    ) { viewModel.onEvent(ProgrammerEvent.OnInput(digit)) }
}
