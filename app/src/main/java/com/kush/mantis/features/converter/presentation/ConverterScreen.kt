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
        Spacer(modifier = Modifier.height(16.dp))

        // Upper Display Area
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.35f)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            // Top Box (Input Value)
            Card(
                modifier = Modifier.fillMaxWidth().weight(1f),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize().padding(16.dp)
                ) {
                    Text(
                        text = "Input",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                        modifier = Modifier.align(Alignment.TopStart)
                    )
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
                        Text(
                            text = inputValue.ifEmpty { "0" },
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))

            // Bottom Box (Output Value)
            Card(
                modifier = Modifier.fillMaxWidth().weight(1f),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize().padding(16.dp)
                ) {
                    Text(
                        text = "Output",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                        modifier = Modifier.align(Alignment.TopStart)
                    )
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
                        Text(
                            text = outputValue.ifEmpty { "0" },
                            fontSize = 48.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1
                        )
                    }
                }
            }
        }

        // PillSelector, Floating Bar & Keypad Area
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.65f)
        ) {
            com.kush.mantis.core.ui.components.PillSelector(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp).padding(bottom = 12.dp),
                items = com.kush.mantis.features.converter.domain.UnitDefinitions.Categories.keys.toList(),
                selectedItem = category,
                onItemSelected = { selected ->
                    viewModel.onEvent(ConverterEvent.SetCategory(selected))
                }
            )

            // NEW Unit Selectors Bar
            Card(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp).padding(bottom = 12.dp),
                shape = androidx.compose.foundation.shape.CircleShape,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // From Unit
                    Box(
                        modifier = Modifier.weight(1f).fillMaxHeight().clickable { fromExpanded = true },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "${fromUnit.name} ▼", color = MantisGreen, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        DropdownMenu(expanded = fromExpanded, onDismissRequest = { fromExpanded = false }, modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant)) {
                            units.forEach { u ->
                                DropdownMenuItem(text = { Text(u.name) }, onClick = { viewModel.onEvent(ConverterEvent.SetFromUnit(u)); fromExpanded = false })
                            }
                        }
                    }

                    // Divider
                    Box(modifier = Modifier.width(1.dp).fillMaxHeight(0.6f).background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f)))

                    // To Unit
                    Box(
                        modifier = Modifier.weight(1f).fillMaxHeight().clickable { toExpanded = true },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "${toUnit.name} ▼", color = MantisGreen, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        DropdownMenu(expanded = toExpanded, onDismissRequest = { toExpanded = false }, modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant)) {
                            units.forEach { u ->
                                DropdownMenuItem(text = { Text(u.name) }, onClick = { viewModel.onEvent(ConverterEvent.SetToUnit(u)); toExpanded = false })
                            }
                        }
                    }
                }
            }
            // Keypad Area
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val rowModifier = Modifier.weight(1f)
                val disabledColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)

                // Row 1
                Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    CalcButton("7", Modifier.weight(1f)) { viewModel.onEvent(ConverterEvent.OnInput("7")) }
                    CalcButton("8", Modifier.weight(1f)) { viewModel.onEvent(ConverterEvent.OnInput("8")) }
                    CalcButton("9", Modifier.weight(1f)) { viewModel.onEvent(ConverterEvent.OnInput("9")) }
                    CalcButton("(", Modifier.weight(1f), textColor = disabledColor) {}
                    CalcButton(")", Modifier.weight(1f), textColor = disabledColor) {}
                }
                // Row 2
                Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    CalcButton("4", Modifier.weight(1f)) { viewModel.onEvent(ConverterEvent.OnInput("4")) }
                    CalcButton("5", Modifier.weight(1f)) { viewModel.onEvent(ConverterEvent.OnInput("5")) }
                    CalcButton("6", Modifier.weight(1f)) { viewModel.onEvent(ConverterEvent.OnInput("6")) }
                    CalcButton("+", Modifier.weight(1f), textColor = disabledColor) {}
                    CalcButton("-", Modifier.weight(1f), textColor = disabledColor) {}
                }
                // Row 3
                Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    CalcButton("1", Modifier.weight(1f)) { viewModel.onEvent(ConverterEvent.OnInput("1")) }
                    CalcButton("2", Modifier.weight(1f)) { viewModel.onEvent(ConverterEvent.OnInput("2")) }
                    CalcButton("3", Modifier.weight(1f)) { viewModel.onEvent(ConverterEvent.OnInput("3")) }
                    CalcButton("÷", Modifier.weight(1f), textColor = disabledColor) {}
                    CalcButton("×", Modifier.weight(1f), textColor = disabledColor) {}
                }
                // Row 4
                Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    CalcButton("C", Modifier.weight(1f), textColor = AccentRed) { viewModel.onEvent(ConverterEvent.OnClear) }
                    CalcButton("0", Modifier.weight(1f)) { viewModel.onEvent(ConverterEvent.OnInput("0")) }
                    CalcButton(".", Modifier.weight(1f)) { viewModel.onEvent(ConverterEvent.OnInput(".")) }
                    CalcButton("⌫", Modifier.weight(1f), textColor = AccentOrange) { viewModel.onEvent(ConverterEvent.OnDelete) }
                    CalcButton("=", Modifier.weight(1f), color = MantisGreen, textColor = Color.Black) {}
                }
            }
        }
    }
}
