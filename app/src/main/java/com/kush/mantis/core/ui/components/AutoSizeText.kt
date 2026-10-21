package com.kush.mantis.core.ui.components

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.isSpecified
import androidx.compose.ui.unit.sp

/**
 * Single-line text that shrinks until it fits its width instead of wrapping or overlapping.
 * Keeps long results and large system font sizes readable.
 */
@Composable
fun AutoSizeText(
    text: String,
    fontSize: TextUnit,
    modifier: Modifier = Modifier,
    minFontSize: TextUnit = 10.sp,
    fontWeight: FontWeight? = null,
    color: Color = Color.Unspecified,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textAlign: TextAlign? = null,
    contentAlignment: Alignment = Alignment.Center
) {
    BoxWithConstraints(modifier = modifier, contentAlignment = contentAlignment) {
        val density = LocalDensity.current
        // Restart sizing whenever the text, width or system font scale changes
        var scale by remember(text, fontSize, maxWidth, density.fontScale) { mutableFloatStateOf(1f) }
        var ready by remember(text, fontSize, maxWidth, density.fontScale) { mutableStateOf(false) }

        Text(
            text = text,
            fontSize = fontSize * scale,
            fontWeight = fontWeight,
            color = color,
            letterSpacing = if (letterSpacing.isSpecified) letterSpacing * scale else letterSpacing,
            textAlign = textAlign,
            maxLines = 1,
            softWrap = false,
            style = LocalTextStyle.current,
            onTextLayout = { layout ->
                if (layout.hasVisualOverflow && fontSize.value * scale > minFontSize.value) {
                    scale *= 0.9f
                } else {
                    ready = true
                }
            },
            // Hide intermediate oversized frames while shrinking
            modifier = Modifier.drawWithContent { if (ready) drawContent() }
        )
    }
}
