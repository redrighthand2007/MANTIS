package com.kush.mantis.features.converter.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.kush.mantis.core.ui.components.AutoSizeText
import com.kush.mantis.core.ui.components.CalculatorLayout
import com.kush.mantis.core.ui.components.ClearKey
import com.kush.mantis.core.ui.components.DeleteKey
import com.kush.mantis.core.ui.components.DisplayCard
import com.kush.mantis.core.ui.components.DisplayColumn
import com.kush.mantis.core.ui.components.DisplayValue
import com.kush.mantis.core.ui.components.Key
import com.kush.mantis.core.ui.components.KeypadRow
import com.kush.mantis.core.ui.components.KeypadSlot
import com.kush.mantis.core.ui.components.OperatorKey
import com.kush.mantis.core.ui.components.PillSelector
import com.kush.mantis.features.converter.domain.UnitDefinition
import com.kush.mantis.features.converter.domain.UnitDefinitions
import com.kush.mantis.features.programmer.domain.BaseConverter
import com.kush.mantis.ui.theme.MantisGreen

@Composable
fun ConverterScreen(viewModel: ConverterViewModel = hiltViewModel()) {
    val category by viewModel.category.collectAsState()
    val units by viewModel.units.collectAsState()
    val fromUnit by viewModel.fromUnit.collectAsState()
    val toUnit by viewModel.toUnit.collectAsState()
    val inputValue by viewModel.inputValue.collectAsState()
    val outputValue by viewModel.outputValue.collectAsState()
    val input = { key: String -> viewModel.onEvent(ConverterEvent.OnInput(key)) }

    CalculatorLayout(
        display = { modifier ->
            DisplayColumn(modifier) {
                DisplayCard(label = "Input") { DisplayValue(inputValue.ifEmpty { "0" }, fontSize = 32.sp) }
                DisplayCard(label = "Output") { DisplayValue(outputValue.ifEmpty { "0" }, fontSize = 48.sp) }
            }
        }
    ) {
        KeypadSlot {
            PillSelector(
                items = UnitDefinitions.Categories.keys.toList(),
                selectedItem = category,
                onItemSelected = { viewModel.onEvent(ConverterEvent.SetCategory(it)) },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
            )
        }
        KeypadSlot {
            UnitSelectorBar(
                units = units,
                fromUnit = fromUnit,
                toUnit = toUnit,
                onFromSelected = { viewModel.onEvent(ConverterEvent.SetFromUnit(it)) },
                onToSelected = { viewModel.onEvent(ConverterEvent.SetToUnit(it)) }
            )
        }

        if (category == "Base") {
            // Hex digits replace the side keys; digits invalid for the input base are disabled
            val base = fromUnit.toBase.toInt()
            val digitKey: @Composable RowScope.(String) -> Unit = { digit ->
                Key(digit, enabled = BaseConverter.isValidDigit(digit[0], base)) { input(digit) }
            }
            KeypadRow { listOf("7", "8", "9", "A", "B").forEach { digitKey(it) } }
            KeypadRow { listOf("4", "5", "6", "C", "D").forEach { digitKey(it) } }
            KeypadRow { listOf("1", "2", "3", "E", "F").forEach { digitKey(it) } }
            KeypadRow {
                ClearKey(symbol = "AC", weight = 2f) { viewModel.onEvent(ConverterEvent.OnClear) }
                digitKey("0")
                OperatorKey("⇅") { viewModel.onEvent(ConverterEvent.SwapUnits) }
                DeleteKey { viewModel.onEvent(ConverterEvent.OnDelete) }
            }
        } else {
            // Results update live, so the side column holds delete, sign and swap instead of "="
            KeypadRow {
                listOf("7", "8", "9").forEach { Key(it) { input(it) } }
                DeleteKey { viewModel.onEvent(ConverterEvent.OnDelete) }
            }
            KeypadRow {
                listOf("4", "5", "6").forEach { Key(it) { input(it) } }
                OperatorKey("±") { viewModel.onEvent(ConverterEvent.ToggleSign) }
            }
            KeypadRow {
                listOf("1", "2", "3").forEach { Key(it) { input(it) } }
                OperatorKey("⇅") { viewModel.onEvent(ConverterEvent.SwapUnits) }
            }
            KeypadRow {
                Key("0") { input("0") }
                Key(".") { input(".") }
                ClearKey(symbol = "AC", weight = 2f) { viewModel.onEvent(ConverterEvent.OnClear) }
            }
        }
    }
}

/** Pill-shaped bar with "from" and "to" unit dropdowns. */
@Composable
private fun UnitSelectorBar(
    units: List<UnitDefinition>,
    fromUnit: UnitDefinition,
    toUnit: UnitDefinition,
    onFromSelected: (UnitDefinition) -> Unit,
    onToSelected: (UnitDefinition) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
        shape = CircleShape,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            UnitDropdown(selected = fromUnit, units = units, onSelected = onFromSelected)
            Box(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight(0.6f)
                    .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f))
            )
            UnitDropdown(selected = toUnit, units = units, onSelected = onToSelected)
        }
    }
}

@Composable
private fun RowScope.UnitDropdown(
    selected: UnitDefinition,
    units: List<UnitDefinition>,
    onSelected: (UnitDefinition) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .clickable { expanded = true },
        contentAlignment = Alignment.Center
    ) {
        AutoSizeText(
            text = "${selected.name} ▼",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MantisGreen,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            units.forEach { unit ->
                DropdownMenuItem(
                    text = { Text(unit.name) },
                    onClick = {
                        onSelected(unit)
                        expanded = false
                    }
                )
            }
        }
    }
}
