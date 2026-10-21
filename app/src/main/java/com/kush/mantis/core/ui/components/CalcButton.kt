package com.kush.mantis.core.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kush.mantis.core.util.HapticHelper

// What screen readers announce for symbol keys ("C" is omitted: it is also a hex digit)
private val spokenLabels = mapOf(
    "⌫" to "Delete",
    "÷" to "Divide",
    "×" to "Multiply",
    "+" to "Plus",
    "-" to "Minus",
    "=" to "Equals",
    "." to "Decimal point",
    "(" to "Open bracket",
    ")" to "Close bracket",
    "√" to "Square root",
    "³√" to "Cube root",
    "xʸ" to "Power",
    "π" to "Pi",
    "±" to "Change sign",
    "⇅" to "Swap units",
    "<<" to "Shift left",
    ">>" to "Shift right"
)

@Composable
fun CalcButton(
    symbol: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.surfaceVariant,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    enabled: Boolean = true,
    hapticEnabled: Boolean = true,
    contentDescription: String? = null,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val view = LocalView.current

    val scale = if (isPressed) 0.95f else 1f
    val label = contentDescription ?: spokenLabels[symbol] ?: symbol

    Surface(
        modifier = modifier
            .padding(4.dp)
            .scale(scale)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                role = Role.Button
            ) {
                HapticHelper.performHapticFeedback(view, hapticEnabled)
                onClick()
            }
            .semantics { this.contentDescription = label },
        shape = RoundedCornerShape(12.dp),
        color = color,
        tonalElevation = 2.dp,
        shadowElevation = 2.dp
    ) {
        AutoSizeText(
            text = symbol,
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            color = if (enabled) textColor else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 4.dp)
        )
    }
}
