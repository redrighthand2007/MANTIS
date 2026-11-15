package com.kush.mantis.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kush.mantis.ui.theme.AccentOrange
import com.kush.mantis.ui.theme.AccentRed
import com.kush.mantis.ui.theme.MantisGreen

/** Shared screen structure: a display area on top (40%) and the keypad below (60%). */
@Composable
fun CalculatorLayout(
    display: @Composable (Modifier) -> Unit,
    keypad: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        display(Modifier.weight(0.4f))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.6f)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            content = keypad
        )
    }
}

/** One row of keys; every row in the keypad gets the same height. */
@Composable
fun ColumnScope.KeypadRow(content: @Composable RowScope.() -> Unit) {
    Row(
        modifier = Modifier.weight(1f),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        content = content
    )
}

/** A keypad row holding a single centred control, such as a base or category selector. */
@Composable
fun ColumnScope.KeypadSlot(content: @Composable BoxScope.() -> Unit) {
    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center,
        content = content
    )
}

@Composable
fun RowScope.Key(
    symbol: String,
    weight: Float = 1f,
    enabled: Boolean = true,
    contentDescription: String? = null,
    onClick: () -> Unit
) {
    CalcButton(
        symbol = symbol,
        modifier = Modifier.weight(weight),
        enabled = enabled,
        contentDescription = contentDescription,
        onClick = onClick
    )
}

@Composable
fun RowScope.OperatorKey(symbol: String, onClick: () -> Unit) {
    CalcButton(symbol = symbol, modifier = Modifier.weight(1f), textColor = MantisGreen, onClick = onClick)
}

@Composable
fun RowScope.ClearKey(symbol: String = "C", weight: Float = 1f, onClick: () -> Unit) {
    CalcButton(
        symbol = symbol,
        modifier = Modifier.weight(weight),
        textColor = AccentRed,
        contentDescription = "Clear",
        onClick = onClick
    )
}

@Composable
fun RowScope.DeleteKey(onClick: () -> Unit) {
    CalcButton(symbol = "⌫", modifier = Modifier.weight(1f), textColor = AccentOrange, onClick = onClick)
}

@Composable
fun RowScope.EqualsKey(onClick: () -> Unit) {
    CalcButton(
        symbol = "=",
        modifier = Modifier.weight(1f),
        color = MantisGreen,
        textColor = Color.Black,
        onClick = onClick
    )
}

/** A key that stays highlighted while its mode is on, e.g. Scientific's "2nd". */
@Composable
fun RowScope.ToggleKey(symbol: String, active: Boolean, contentDescription: String, onClick: () -> Unit) {
    CalcButton(
        symbol = symbol,
        modifier = Modifier.weight(1f),
        color = if (active) MantisGreen else MaterialTheme.colorScheme.surfaceVariant,
        textColor = if (active) Color.Black else MaterialTheme.colorScheme.onSurface,
        contentDescription = contentDescription,
        onClick = onClick
    )
}
