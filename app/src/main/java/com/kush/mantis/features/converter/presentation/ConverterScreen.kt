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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.kush.mantis.core.ui.components.AutoSizeText
import com.kush.mantis.core.ui.components.CalcButton
import com.kush.mantis.core.ui.components.TopHeader
import com.kush.mantis.features.converter.domain.UnitDefinitions
import com.kush.mantis.features.programmer.domain.BaseConverter
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
    // Short windows (split-screen) drop the labels and padding so the numbers are not clipped
    val compact = com.kush.mantis.core.ui.components.isCompactHeight()
    var fromExpanded by remember { mutableStateOf(false) }
    var toExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        // Upper Display Area
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.40f)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Top Box (Input Value)
            Card(
                modifier = Modifier.fillMaxWidth().weight(1f),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize().padding(if (compact) 8.dp else 16.dp)
                ) {
                    if (!compact) Text(
                        text = "Input",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                        modifier = Modifier.align(Alignment.TopStart)
                    )
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
                        AutoSizeText(
                            text = inputValue.ifEmpty { "0" },
                            fontSize = 32.sp,
                            minFontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            contentAlignment = Alignment.CenterEnd,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            // Bottom Box (Output Value)
            Card(
                modifier = Modifier.fillMaxWidth().weight(1f),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize().padding(if (compact) 8.dp else 16.dp)
                ) {
                    if (!compact) Text(
                        text = "Output",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                        modifier = Modifier.align(Alignment.TopStart)
                    )
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
                        AutoSizeText(
                            text = outputValue.ifEmpty { "0" },
                            fontSize = 48.sp,
                            minFontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            contentAlignment = Alignment.CenterEnd,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
        // PillSelector, Floating Bar & Keypad Area
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.60f)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val rowModifier = Modifier.weight(1f)
            val disabledColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)

            // Row 1: PillSelector
            Box(modifier = rowModifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                com.kush.mantis.core.ui.components.PillSelector(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                    items = com.kush.mantis.features.converter.domain.UnitDefinitions.Categories.keys.toList(),
                    selectedItem = category,
                    onItemSelected = { selected -> viewModel.onEvent(ConverterEvent.SetCategory(selected)) }
                )
            }

            // Row 2: Unit Selectors Bar
            Box(modifier = rowModifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                    shape = androidx.compose.foundation.shape.CircleShape,
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // From Unit
                        Box(
                            modifier = Modifier.weight(1f).fillMaxHeight().clickable { fromExpanded = true },
                            contentAlignment = Alignment.Center
                        ) {
                            AutoSizeText(text = "${fromUnit.name} ▼", color = MantisGreen, fontWeight = FontWeight.Bold, fontSize = 14.sp, modifier = Modifier.padding(horizontal = 8.dp))
                            DropdownMenu(expanded = fromExpanded, onDismissRequest = { fromExpanded = false }, modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant)) {
                                units.forEach { u -> DropdownMenuItem(text = { Text(u.name) }, onClick = { viewModel.onEvent(ConverterEvent.SetFromUnit(u)); fromExpanded = false }) }
                            }
                        }

                        // Divider
                        Box(modifier = Modifier.width(1.dp).fillMaxHeight(0.6f).background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f)))

                        // To Unit
                        Box(
                            modifier = Modifier.weight(1f).fillMaxHeight().clickable { toExpanded = true },
                            contentAlignment = Alignment.Center
                        ) {
                            AutoSizeText(text = "${toUnit.name} ▼", color = MantisGreen, fontWeight = FontWeight.Bold, fontSize = 14.sp, modifier = Modifier.padding(horizontal = 8.dp))
                            DropdownMenu(expanded = toExpanded, onDismissRequest = { toExpanded = false }, modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant)) {
                                units.forEach { u -> DropdownMenuItem(text = { Text(u.name) }, onClick = { viewModel.onEvent(ConverterEvent.SetToUnit(u)); toExpanded = false }) }
                            }
                        }
                    }
                }
            }

            if (category == "Base") {
                // Base: hex digits instead of arithmetic keys; digits invalid for the input base are disabled
                val base = fromUnit.toBase.toInt()
                Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("7", "8", "9", "A", "B").forEach { DigitKey(it, base, viewModel) }
                }
                Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("4", "5", "6", "C", "D").forEach { DigitKey(it, base, viewModel) }
                }
                Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("1", "2", "3", "E", "F").forEach { DigitKey(it, base, viewModel) }
                }
                Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    CalcButton("AC", Modifier.weight(2f), textColor = AccentRed, contentDescription = "Clear") { viewModel.onEvent(ConverterEvent.OnClear) }
                    DigitKey("0", base, viewModel)
                    CalcButton("⇅", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ConverterEvent.SwapUnits) }
                    CalcButton("⌫", Modifier.weight(1f), textColor = AccentOrange) { viewModel.onEvent(ConverterEvent.OnDelete) }
                }
            } else {
                // Results update live, so the side column holds sign, swap, delete and clear instead of "="
                Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("7", "8", "9").forEach { NumberKey(it, viewModel) }
                    CalcButton("⌫", Modifier.weight(1f), textColor = AccentOrange) { viewModel.onEvent(ConverterEvent.OnDelete) }
                }
                Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("4", "5", "6").forEach { NumberKey(it, viewModel) }
                    CalcButton("±", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ConverterEvent.ToggleSign) }
                }
                Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("1", "2", "3").forEach { NumberKey(it, viewModel) }
                    CalcButton("⇅", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(ConverterEvent.SwapUnits) }
                }
                Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    NumberKey("0", viewModel)
                    NumberKey(".", viewModel)
                    CalcButton("AC", Modifier.weight(2f), textColor = AccentRed, contentDescription = "Clear") { viewModel.onEvent(ConverterEvent.OnClear) }
                }
            }
        }
    }
}

@Composable
private fun RowScope.NumberKey(key: String, viewModel: ConverterViewModel) {
    CalcButton(key, Modifier.weight(1f)) { viewModel.onEvent(ConverterEvent.OnInput(key)) }
}

@Composable
private fun RowScope.DigitKey(digit: String, base: Int, viewModel: ConverterViewModel) {
    CalcButton(
        digit,
        Modifier.weight(1f),
        enabled = BaseConverter.isValidDigit(digit[0], base)
    ) { viewModel.onEvent(ConverterEvent.OnInput(digit)) }
}
