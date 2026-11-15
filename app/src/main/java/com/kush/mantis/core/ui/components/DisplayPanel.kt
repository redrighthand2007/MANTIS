package com.kush.mantis.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/** Editable expression on top, result below. Used by Basic, Scientific and Programmer. */
@Composable
fun DisplayPanel(
    expression: TextFieldValue,
    onExpressionChange: (TextFieldValue) -> Unit,
    result: String,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false
) {
    val compact = isCompactHeight()

    DisplayColumn(modifier) {
        DisplayCard(label = "Input") {
            DisableSoftKeyboard {
                // Long expressions scroll inside the field instead of overflowing the card
                BasicTextField(
                    value = expression,
                    onValueChange = onExpressionChange,
                    readOnly = readOnly,
                    maxLines = if (compact) 1 else 2,
                    textStyle = TextStyle(
                        fontSize = if (compact) 22.sp else 32.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.End
                    ),
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        DisplayCard(label = "Output") {
            SelectionContainer {
                DisplayValue(text = result, fontSize = 48.sp)
            }
        }
    }
}

/** Stacks display cards with spacing that tightens in short windows. */
@Composable
fun DisplayColumn(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    val compact = isCompactHeight()
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = if (compact) 4.dp else 8.dp),
        verticalArrangement = Arrangement.spacedBy(if (compact) 4.dp else 8.dp),
        content = content
    )
}

/**
 * A labelled card whose content sits at the bottom-right, like a calculator display.
 * In short windows (split-screen) the label and padding are dropped so the numbers are not clipped.
 */
@Composable
fun ColumnScope.DisplayCard(label: String, content: @Composable BoxScope.() -> Unit) {
    val compact = isCompactHeight()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(if (compact) 8.dp else 16.dp)
        ) {
            if (!compact) {
                Text(
                    text = label,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    modifier = Modifier.align(Alignment.TopStart)
                )
            }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomEnd,
                content = content
            )
        }
    }
}

/** Bold, right-aligned number that shrinks to fit the card width. */
@Composable
fun DisplayValue(text: String, fontSize: TextUnit) {
    AutoSizeText(
        text = text,
        fontSize = fontSize,
        minFontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface,
        contentAlignment = Alignment.CenterEnd,
        modifier = Modifier.fillMaxWidth()
    )
}
