package com.kush.mantis.features.converter.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kush.mantis.core.ui.components.CalcButton
import com.kush.mantis.core.ui.components.TopHeader
import com.kush.mantis.features.converter.domain.UnitDefinitions
import com.kush.mantis.ui.theme.AccentOrange
import com.kush.mantis.ui.theme.AccentRed
import com.kush.mantis.ui.theme.MantisGreen

@Composable
fun ConverterScreen(
    viewModel: ConverterViewModel = hiltViewModel()
) {
    val category by viewModel.category.collectAsState()
    val units by viewModel.units.collectAsState()
    val fromUnit by viewModel.fromUnit.collectAsState()
    val toUnit by viewModel.toUnit.collectAsState()
    val inputValue by viewModel.inputValue.collectAsState()
    val outputValue by viewModel.outputValue.collectAsState()

    var categoryExpanded by remember { mutableStateOf(false) }
    var fromExpanded by remember { mutableStateOf(false) }
    var toExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopHeader(
            title = "Converter",
            trailingContent = {
                Box {
                    Text(
                        text = "$category ▼",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MantisGreen,
                        modifier = Modifier.clickable { categoryExpanded = true }.padding(8.dp)
                    )
                    DropdownMenu(expanded = categoryExpanded, onDismissRequest = { categoryExpanded = false }, modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant)) {
                        UnitDefinitions.Categories.keys.forEach { cat ->
                            DropdownMenuItem(
                                text = { Text(cat) },
                                onClick = { viewModel.onEvent(ConverterEvent.SetCategory(cat)); categoryExpanded = false }
                            )
                        }
                    }
                }
            }
        )

        // Upper Display Area
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.35f)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            // Top Box (From Unit)
            Card(
                modifier = Modifier.fillMaxWidth().weight(1f),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.End
                ) {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
                        Text(
                            text = "${fromUnit.name} ▼",
                            fontSize = 16.sp,
                            color = MantisGreen,
                            modifier = Modifier.clickable { fromExpanded = true }.padding(vertical = 4.dp)
                        )
                        DropdownMenu(expanded = fromExpanded, onDismissRequest = { fromExpanded = false }, modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant)) {
                            units.forEach { u ->
                                DropdownMenuItem(text = { Text(u.name) }, onClick = { viewModel.onEvent(ConverterEvent.SetFromUnit(u)); fromExpanded = false })
                            }
                        }
                    }
                    Text(
                        text = inputValue.ifEmpty { "0" },
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))

            // Bottom Box (To Unit)
            Card(
                modifier = Modifier.fillMaxWidth().weight(1f),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.End
                ) {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
                        Text(
                            text = "${toUnit.name} ▼",
                            fontSize = 16.sp,
                            color = MantisGreen,
                            modifier = Modifier.clickable { toExpanded = true }.padding(vertical = 4.dp)
                        )
                        DropdownMenu(expanded = toExpanded, onDismissRequest = { toExpanded = false }, modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant)) {
                            units.forEach { u ->
                                DropdownMenuItem(text = { Text(u.name) }, onClick = { viewModel.onEvent(ConverterEvent.SetToUnit(u)); toExpanded = false })
                            }
                        }
                    }
                    Text(
                        text = outputValue.ifEmpty { "0" },
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1
                    )
                }
            }
        }

        // Keypad Area
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.65f)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val rowModifier = Modifier.weight(1f)
            val isHex = category == "Base" && fromUnit.name == "Hexadecimal"
            
            if (category == "Base") {
                Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    CalcButton("A", Modifier.weight(1f), textColor = if(isHex) MaterialTheme.colorScheme.onSurface else Color.Gray) { if(isHex) viewModel.onEvent(ConverterEvent.OnInput("A")) }
                    CalcButton("B", Modifier.weight(1f), textColor = if(isHex) MaterialTheme.colorScheme.onSurface else Color.Gray) { if(isHex) viewModel.onEvent(ConverterEvent.OnInput("B")) }
                    CalcButton("C", Modifier.weight(1f), textColor = if(isHex) MaterialTheme.colorScheme.onSurface else Color.Gray) { if(isHex) viewModel.onEvent(ConverterEvent.OnInput("C")) }
                    CalcButton("D", Modifier.weight(1f), textColor = if(isHex) MaterialTheme.colorScheme.onSurface else Color.Gray) { if(isHex) viewModel.onEvent(ConverterEvent.OnInput("D")) }
                }
                Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    CalcButton("E", Modifier.weight(1f), textColor = if(isHex) MaterialTheme.colorScheme.onSurface else Color.Gray) { if(isHex) viewModel.onEvent(ConverterEvent.OnInput("E")) }
                    CalcButton("F", Modifier.weight(1f), textColor = if(isHex) MaterialTheme.colorScheme.onSurface else Color.Gray) { if(isHex) viewModel.onEvent(ConverterEvent.OnInput("F")) }
                    CalcButton("", Modifier.weight(1f)) {}
                    CalcButton("", Modifier.weight(1f)) {}
                }
            }

            // Normal Keypad
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("7", Modifier.weight(1f)) { viewModel.onEvent(ConverterEvent.OnInput("7")) }
                CalcButton("8", Modifier.weight(1f)) { viewModel.onEvent(ConverterEvent.OnInput("8")) }
                CalcButton("9", Modifier.weight(1f)) { viewModel.onEvent(ConverterEvent.OnInput("9")) }
                CalcButton("C", Modifier.weight(1f), textColor = AccentRed) { viewModel.onEvent(ConverterEvent.OnClear) }
            }
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("4", Modifier.weight(1f)) { viewModel.onEvent(ConverterEvent.OnInput("4")) }
                CalcButton("5", Modifier.weight(1f)) { viewModel.onEvent(ConverterEvent.OnInput("5")) }
                CalcButton("6", Modifier.weight(1f)) { viewModel.onEvent(ConverterEvent.OnInput("6")) }
                CalcButton("-", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ConverterEvent.OnInput("-")) }
            }
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("1", Modifier.weight(1f)) { viewModel.onEvent(ConverterEvent.OnInput("1")) }
                CalcButton("2", Modifier.weight(1f)) { viewModel.onEvent(ConverterEvent.OnInput("2")) }
                CalcButton("3", Modifier.weight(1f)) { viewModel.onEvent(ConverterEvent.OnInput("3")) }
                CalcButton(".", Modifier.weight(1f)) { viewModel.onEvent(ConverterEvent.OnInput(".")) }
            }
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("0", Modifier.weight(3f)) { viewModel.onEvent(ConverterEvent.OnInput("0")) }
                CalcButton("⌫", Modifier.weight(1f), textColor = AccentOrange) { viewModel.onEvent(ConverterEvent.OnDelete) }
            }
        }
    }
}
