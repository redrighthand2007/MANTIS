package com.kush.mantis.features.converter.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kush.mantis.core.ui.components.CalcButton
import com.kush.mantis.features.converter.domain.UnitDefinition
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
        // Upper Display Area (Weight: 0.35f)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.35f)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.End
        ) {
            // Top Row: Dropdowns (Category, From, To)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Category
                Box {
                    Text(
                        text = "$category ▼",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MantisGreen,
                        modifier = Modifier.clickable { categoryExpanded = true }.padding(vertical = 8.dp)
                    )
                    DropdownMenu(expanded = categoryExpanded, onDismissRequest = { categoryExpanded = false }) {
                        UnitDefinitions.Categories.keys.forEach { cat ->
                            DropdownMenuItem(
                                text = { Text(cat) },
                                onClick = { viewModel.onEvent(ConverterEvent.SetCategory(cat)); categoryExpanded = false }
                            )
                        }
                    }
                }
                
                // From & To Dropdowns
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    // From
                    Box {
                        Text(
                            text = "${fromUnit.name} ▼",
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.clickable { fromExpanded = true }.padding(vertical = 8.dp)
                        )
                        DropdownMenu(expanded = fromExpanded, onDismissRequest = { fromExpanded = false }) {
                            units.forEach { u ->
                                DropdownMenuItem(text = { Text(u.name) }, onClick = { viewModel.onEvent(ConverterEvent.SetFromUnit(u)); fromExpanded = false })
                            }
                        }
                    }

                    Text("→", fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(vertical = 8.dp))

                    // To
                    Box {
                        Text(
                            text = "${toUnit.name} ▼",
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.clickable { toExpanded = true }.padding(vertical = 8.dp)
                        )
                        DropdownMenu(expanded = toExpanded, onDismissRequest = { toExpanded = false }) {
                            units.forEach { u ->
                                DropdownMenuItem(text = { Text(u.name) }, onClick = { viewModel.onEvent(ConverterEvent.SetToUnit(u)); toExpanded = false })
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Main Display Output
            Text(
                text = "${inputValue.ifEmpty { "0" }} ${fromUnit.symbol}",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "= ${outputValue.ifEmpty { "0" }} ${toUnit.symbol}",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1
            )
        }

        // Keypad Area (Weight: 0.65f) - Exact match to BasicScreen
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
                CalcButton("C", Modifier.weight(1f), textColor = AccentRed) { viewModel.onEvent(ConverterEvent.OnClear) }
                CalcButton("(", Modifier.weight(1f), textColor = MantisGreen) { }
                CalcButton(")", Modifier.weight(1f), textColor = MantisGreen) { }
                CalcButton("÷", Modifier.weight(1f), textColor = MantisGreen) { }
            }
            // Row 2
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("7", Modifier.weight(1f)) { viewModel.onEvent(ConverterEvent.OnInput("7")) }
                CalcButton("8", Modifier.weight(1f)) { viewModel.onEvent(ConverterEvent.OnInput("8")) }
                CalcButton("9", Modifier.weight(1f)) { viewModel.onEvent(ConverterEvent.OnInput("9")) }
                CalcButton("×", Modifier.weight(1f), textColor = MantisGreen) { }
            }
            // Row 3
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("4", Modifier.weight(1f)) { viewModel.onEvent(ConverterEvent.OnInput("4")) }
                CalcButton("5", Modifier.weight(1f)) { viewModel.onEvent(ConverterEvent.OnInput("5")) }
                CalcButton("6", Modifier.weight(1f)) { viewModel.onEvent(ConverterEvent.OnInput("6")) }
                CalcButton("-", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ConverterEvent.OnInput("-")) } // Allow minus for negative temps
            }
            // Row 4
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("1", Modifier.weight(1f)) { viewModel.onEvent(ConverterEvent.OnInput("1")) }
                CalcButton("2", Modifier.weight(1f)) { viewModel.onEvent(ConverterEvent.OnInput("2")) }
                CalcButton("3", Modifier.weight(1f)) { viewModel.onEvent(ConverterEvent.OnInput("3")) }
                CalcButton("+", Modifier.weight(1f), textColor = MantisGreen) { }
            }
            // Row 5
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton(".", Modifier.weight(1f)) { viewModel.onEvent(ConverterEvent.OnInput(".")) }
                CalcButton("0", Modifier.weight(1f)) { viewModel.onEvent(ConverterEvent.OnInput("0")) }
                CalcButton("⌫", Modifier.weight(1f), textColor = AccentOrange) { viewModel.onEvent(ConverterEvent.OnDelete) }
                CalcButton("=", Modifier.weight(1f), color = MantisGreen, textColor = Color.Black) { }
            }
        }
    }
}
