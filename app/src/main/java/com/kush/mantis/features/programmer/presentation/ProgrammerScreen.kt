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
import androidx.hilt.navigation.compose.hiltViewModel
import com.kush.mantis.core.ui.components.CalcButton
import com.kush.mantis.core.ui.components.TopHeader
import com.kush.mantis.core.ui.components.DisplayPanel
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
        Spacer(modifier = Modifier.height(16.dp))

        DisplayPanel(
            expression = expression,
            onExpressionChange = { viewModel.onEvent(ProgrammerEvent.OnExpressionChange(it)) },
            result = result,
            modifier = Modifier.weight(0.40f)
        )
        // 6 Equal Rows (1: PillSelector, 2: Hex, 3-6: Keypad)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.60f)
                .padding(16.dp),
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
                CalcButton("A", Modifier.weight(1f), textColor = if(activeBase == 16) MaterialTheme.colorScheme.onSurface else Color.Gray) { if(activeBase == 16) viewModel.onEvent(ProgrammerEvent.OnInput("A")) }
                CalcButton("B", Modifier.weight(1f), textColor = if(activeBase == 16) MaterialTheme.colorScheme.onSurface else Color.Gray) { if(activeBase == 16) viewModel.onEvent(ProgrammerEvent.OnInput("B")) }
                CalcButton("C", Modifier.weight(1f), textColor = if(activeBase == 16) MaterialTheme.colorScheme.onSurface else Color.Gray) { if(activeBase == 16) viewModel.onEvent(ProgrammerEvent.OnInput("C")) }
                CalcButton("D", Modifier.weight(1f), textColor = if(activeBase == 16) MaterialTheme.colorScheme.onSurface else Color.Gray) { if(activeBase == 16) viewModel.onEvent(ProgrammerEvent.OnInput("D")) }
                CalcButton("E", Modifier.weight(1f), textColor = if(activeBase == 16) MaterialTheme.colorScheme.onSurface else Color.Gray) { if(activeBase == 16) viewModel.onEvent(ProgrammerEvent.OnInput("E")) }
                CalcButton("F", Modifier.weight(1f), textColor = if(activeBase == 16) MaterialTheme.colorScheme.onSurface else Color.Gray) { if(activeBase == 16) viewModel.onEvent(ProgrammerEvent.OnInput("F")) }
            }
            
            // Row 3
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("7", Modifier.weight(1f), textColor = if(activeBase >= 8) MaterialTheme.colorScheme.onSurface else Color.Gray) { if(activeBase >= 8) viewModel.onEvent(ProgrammerEvent.OnInput("7")) }
                CalcButton("8", Modifier.weight(1f), textColor = if(activeBase >= 10) MaterialTheme.colorScheme.onSurface else Color.Gray) { if(activeBase >= 10) viewModel.onEvent(ProgrammerEvent.OnInput("8")) }
                CalcButton("9", Modifier.weight(1f), textColor = if(activeBase >= 10) MaterialTheme.colorScheme.onSurface else Color.Gray) { if(activeBase >= 10) viewModel.onEvent(ProgrammerEvent.OnInput("9")) }
                CalcButton("<<", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ProgrammerEvent.OnBitwiseOp("<<")) }
                CalcButton(">>", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ProgrammerEvent.OnBitwiseOp(">>")) }
            }
            
            // Row 4
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("4", Modifier.weight(1f), textColor = if(activeBase >= 8) MaterialTheme.colorScheme.onSurface else Color.Gray) { if(activeBase >= 8) viewModel.onEvent(ProgrammerEvent.OnInput("4")) }
                CalcButton("5", Modifier.weight(1f), textColor = if(activeBase >= 8) MaterialTheme.colorScheme.onSurface else Color.Gray) { if(activeBase >= 8) viewModel.onEvent(ProgrammerEvent.OnInput("5")) }
                CalcButton("6", Modifier.weight(1f), textColor = if(activeBase >= 8) MaterialTheme.colorScheme.onSurface else Color.Gray) { if(activeBase >= 8) viewModel.onEvent(ProgrammerEvent.OnInput("6")) }
                CalcButton("XOR", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ProgrammerEvent.OnBitwiseOp("XOR")) }
                CalcButton("NOT", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ProgrammerEvent.OnBitwiseOp("NOT")) }
            }
            
            // Row 5
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("1", Modifier.weight(1f)) { viewModel.onEvent(ProgrammerEvent.OnInput("1")) }
                CalcButton("2", Modifier.weight(1f), textColor = if(activeBase >= 8) MaterialTheme.colorScheme.onSurface else Color.Gray) { if(activeBase >= 8) viewModel.onEvent(ProgrammerEvent.OnInput("2")) }
                CalcButton("3", Modifier.weight(1f), textColor = if(activeBase >= 8) MaterialTheme.colorScheme.onSurface else Color.Gray) { if(activeBase >= 8) viewModel.onEvent(ProgrammerEvent.OnInput("3")) }
                CalcButton("AND", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ProgrammerEvent.OnBitwiseOp("AND")) }
                CalcButton("OR", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ProgrammerEvent.OnBitwiseOp("OR")) }
            }
            
            // Row 6
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("C", Modifier.weight(1f), textColor = AccentRed) { viewModel.onEvent(ProgrammerEvent.OnClear) }
                CalcButton("0", Modifier.weight(1f)) { viewModel.onEvent(ProgrammerEvent.OnInput("0")) }
                CalcButton(".", Modifier.weight(1f), textColor = Color.Gray) {}
                CalcButton("⌫", Modifier.weight(1f), textColor = AccentOrange) { viewModel.onEvent(ProgrammerEvent.OnDelete) }
                CalcButton("=", Modifier.weight(1f), color = MantisGreen, textColor = Color.Black) { viewModel.onEvent(ProgrammerEvent.OnEquals) }
            }
        }
    }
}
