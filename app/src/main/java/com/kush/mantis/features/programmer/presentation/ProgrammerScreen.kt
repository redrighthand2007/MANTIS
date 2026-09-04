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
        TopHeader(
            title = "Programmer",
            trailingContent = {
                Box {
                    Text(
                        text = "Base $activeBase ▼",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MantisGreen,
                        modifier = Modifier.clickable { baseExpanded = true }.padding(8.dp)
                    )
                    DropdownMenu(expanded = baseExpanded, onDismissRequest = { baseExpanded = false }, modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant)) {
                        DropdownMenuItem(text = { Text("HEX (16)") }, onClick = { viewModel.onEvent(ProgrammerEvent.SetBase(16)); baseExpanded = false })
                        DropdownMenuItem(text = { Text("DEC (10)") }, onClick = { viewModel.onEvent(ProgrammerEvent.SetBase(10)); baseExpanded = false })
                        DropdownMenuItem(text = { Text("OCT (8)") }, onClick = { viewModel.onEvent(ProgrammerEvent.SetBase(8)); baseExpanded = false })
                        DropdownMenuItem(text = { Text("BIN (2)") }, onClick = { viewModel.onEvent(ProgrammerEvent.SetBase(2)); baseExpanded = false })
                    }
                }
            }
        )

        DisplayPanel(
            expression = expression,
            onExpressionChange = { viewModel.onEvent(ProgrammerEvent.OnExpressionChange(it)) },
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
            
            // Hex Letters Row 1
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("A", Modifier.weight(1f), textColor = if(activeBase == 16) MaterialTheme.colorScheme.onSurface else Color.Gray) { if(activeBase == 16) viewModel.onEvent(ProgrammerEvent.OnInput("A")) }
                CalcButton("B", Modifier.weight(1f), textColor = if(activeBase == 16) MaterialTheme.colorScheme.onSurface else Color.Gray) { if(activeBase == 16) viewModel.onEvent(ProgrammerEvent.OnInput("B")) }
                CalcButton("C", Modifier.weight(1f), textColor = if(activeBase == 16) MaterialTheme.colorScheme.onSurface else Color.Gray) { if(activeBase == 16) viewModel.onEvent(ProgrammerEvent.OnInput("C")) }
                CalcButton("NOT", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ProgrammerEvent.OnBitwiseOp("NOT")) }
                CalcButton("C", Modifier.weight(1f), textColor = AccentRed) { viewModel.onEvent(ProgrammerEvent.OnClear) }
            }
            // Hex Letters Row 2
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("D", Modifier.weight(1f), textColor = if(activeBase == 16) MaterialTheme.colorScheme.onSurface else Color.Gray) { if(activeBase == 16) viewModel.onEvent(ProgrammerEvent.OnInput("D")) }
                CalcButton("E", Modifier.weight(1f), textColor = if(activeBase == 16) MaterialTheme.colorScheme.onSurface else Color.Gray) { if(activeBase == 16) viewModel.onEvent(ProgrammerEvent.OnInput("E")) }
                CalcButton("F", Modifier.weight(1f), textColor = if(activeBase == 16) MaterialTheme.colorScheme.onSurface else Color.Gray) { if(activeBase == 16) viewModel.onEvent(ProgrammerEvent.OnInput("F")) }
                CalcButton("<<", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ProgrammerEvent.OnBitwiseOp("<<")) }
                CalcButton(">>", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ProgrammerEvent.OnBitwiseOp(">>")) }
            }
            // Row 7-9
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("7", Modifier.weight(1f), textColor = if(activeBase >= 8) MaterialTheme.colorScheme.onSurface else Color.Gray) { if(activeBase >= 8) viewModel.onEvent(ProgrammerEvent.OnInput("7")) }
                CalcButton("8", Modifier.weight(1f), textColor = if(activeBase >= 10) MaterialTheme.colorScheme.onSurface else Color.Gray) { if(activeBase >= 10) viewModel.onEvent(ProgrammerEvent.OnInput("8")) }
                CalcButton("9", Modifier.weight(1f), textColor = if(activeBase >= 10) MaterialTheme.colorScheme.onSurface else Color.Gray) { if(activeBase >= 10) viewModel.onEvent(ProgrammerEvent.OnInput("9")) }
                CalcButton("AND", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ProgrammerEvent.OnBitwiseOp("AND")) }
                CalcButton("⌫", Modifier.weight(1f), textColor = AccentOrange) { viewModel.onEvent(ProgrammerEvent.OnDelete) }
            }
            // Row 4-6
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("4", Modifier.weight(1f), textColor = if(activeBase >= 8) MaterialTheme.colorScheme.onSurface else Color.Gray) { if(activeBase >= 8) viewModel.onEvent(ProgrammerEvent.OnInput("4")) }
                CalcButton("5", Modifier.weight(1f), textColor = if(activeBase >= 8) MaterialTheme.colorScheme.onSurface else Color.Gray) { if(activeBase >= 8) viewModel.onEvent(ProgrammerEvent.OnInput("5")) }
                CalcButton("6", Modifier.weight(1f), textColor = if(activeBase >= 8) MaterialTheme.colorScheme.onSurface else Color.Gray) { if(activeBase >= 8) viewModel.onEvent(ProgrammerEvent.OnInput("6")) }
                CalcButton("OR", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ProgrammerEvent.OnBitwiseOp("OR")) }
                CalcButton("XOR", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ProgrammerEvent.OnBitwiseOp("XOR")) }
            }
            // Row 1-3
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("1", Modifier.weight(1f)) { viewModel.onEvent(ProgrammerEvent.OnInput("1")) }
                CalcButton("2", Modifier.weight(1f), textColor = if(activeBase >= 8) MaterialTheme.colorScheme.onSurface else Color.Gray) { if(activeBase >= 8) viewModel.onEvent(ProgrammerEvent.OnInput("2")) }
                CalcButton("3", Modifier.weight(1f), textColor = if(activeBase >= 8) MaterialTheme.colorScheme.onSurface else Color.Gray) { if(activeBase >= 8) viewModel.onEvent(ProgrammerEvent.OnInput("3")) }
                CalcButton("0", Modifier.weight(1f)) { viewModel.onEvent(ProgrammerEvent.OnInput("0")) }
                CalcButton("=", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ProgrammerEvent.OnEquals) }
            }
        }
    }
}
