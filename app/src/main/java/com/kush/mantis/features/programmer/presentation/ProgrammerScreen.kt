package com.kush.mantis.features.programmer.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kush.mantis.core.ui.components.CalcButton
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Display Area (Bases)
        Column(
            modifier = Modifier
                .weight(0.35f)
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            BaseRow("DEC", 10, activeBase, BaseConverter.convert(currentValue, 10)) { viewModel.onEvent(ProgrammerEvent.SetBase(10)) }
            BaseRow("HEX", 16, activeBase, BaseConverter.convert(currentValue, 16)) { viewModel.onEvent(ProgrammerEvent.SetBase(16)) }
            BaseRow("OCT", 8, activeBase, BaseConverter.convert(currentValue, 8)) { viewModel.onEvent(ProgrammerEvent.SetBase(8)) }
            BaseRow("BIN", 2, activeBase, BaseConverter.convert(currentValue, 2)) { viewModel.onEvent(ProgrammerEvent.SetBase(2)) }
        }

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
                CalcButton("<<", Modifier.weight(1f), textColor = MantisGreen) { /* Shift */ }
                CalcButton(">>", Modifier.weight(1f), textColor = MantisGreen) { /* Shift */ }
            }
            // Row 7-9
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("7", Modifier.weight(1f), textColor = if(activeBase >= 8) MaterialTheme.colorScheme.onSurface else Color.Gray) { if(activeBase >= 8) viewModel.onEvent(ProgrammerEvent.OnInput("7")) }
                CalcButton("8", Modifier.weight(1f), textColor = if(activeBase >= 10) MaterialTheme.colorScheme.onSurface else Color.Gray) { if(activeBase >= 10) viewModel.onEvent(ProgrammerEvent.OnInput("8")) }
                CalcButton("9", Modifier.weight(1f), textColor = if(activeBase >= 10) MaterialTheme.colorScheme.onSurface else Color.Gray) { if(activeBase >= 10) viewModel.onEvent(ProgrammerEvent.OnInput("9")) }
                CalcButton("AND", Modifier.weight(1f), textColor = MantisGreen) { }
                CalcButton("⌫", Modifier.weight(1f), textColor = AccentOrange) { viewModel.onEvent(ProgrammerEvent.OnDelete) }
            }
            // Row 4-6
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("4", Modifier.weight(1f), textColor = if(activeBase >= 8) MaterialTheme.colorScheme.onSurface else Color.Gray) { if(activeBase >= 8) viewModel.onEvent(ProgrammerEvent.OnInput("4")) }
                CalcButton("5", Modifier.weight(1f), textColor = if(activeBase >= 8) MaterialTheme.colorScheme.onSurface else Color.Gray) { if(activeBase >= 8) viewModel.onEvent(ProgrammerEvent.OnInput("5")) }
                CalcButton("6", Modifier.weight(1f), textColor = if(activeBase >= 8) MaterialTheme.colorScheme.onSurface else Color.Gray) { if(activeBase >= 8) viewModel.onEvent(ProgrammerEvent.OnInput("6")) }
                CalcButton("OR", Modifier.weight(1f), textColor = MantisGreen) { }
                CalcButton("XOR", Modifier.weight(1f), textColor = MantisGreen) { }
            }
            // Row 1-3
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("1", Modifier.weight(1f)) { viewModel.onEvent(ProgrammerEvent.OnInput("1")) }
                CalcButton("2", Modifier.weight(1f), textColor = if(activeBase >= 8) MaterialTheme.colorScheme.onSurface else Color.Gray) { if(activeBase >= 8) viewModel.onEvent(ProgrammerEvent.OnInput("2")) }
                CalcButton("3", Modifier.weight(1f), textColor = if(activeBase >= 8) MaterialTheme.colorScheme.onSurface else Color.Gray) { if(activeBase >= 8) viewModel.onEvent(ProgrammerEvent.OnInput("3")) }
                CalcButton("0", Modifier.weight(2.05f)) { viewModel.onEvent(ProgrammerEvent.OnInput("0")) }
            }
        }
    }
}

@Composable
fun BaseRow(label: String, base: Int, activeBase: Int, value: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = if (activeBase == base) MantisGreen else MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            fontSize = 18.sp,
            color = if (activeBase == base) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
