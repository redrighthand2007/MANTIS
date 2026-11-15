package com.kush.mantis.core.ui.components

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.isSpecified
import androidx.compose.ui.unit.sp

/**
 * Single-line text that shrinks until it fits its width instead of wrapping or overlapping.
 * The size is measured before the first frame is drawn, so the text never flickers or pops in.
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
        val textMeasurer = rememberTextMeasurer()
        val baseStyle = LocalTextStyle.current
        val maxWidthPx = constraints.maxWidth

        val scale = remember(text, fontSize, minFontSize, fontWeight, letterSpacing, maxWidthPx, baseStyle) {
            fun styleAt(factor: Float) = baseStyle.merge(
                TextStyle(
                    fontSize = fontSize * factor,
                    fontWeight = fontWeight,
                    letterSpacing = if (letterSpacing.isSpecified) letterSpacing * factor else letterSpacing
                )
            )

            var fitted = 1f
            while (fontSize.value * fitted > minFontSize.value) {
                val width = textMeasurer.measure(text, styleAt(fitted), maxLines = 1, softWrap = false).size.width
                if (width <= maxWidthPx) break
                fitted *= 0.9f
            }
            fitted
        }

        Text(
            text = text,
            fontSize = fontSize * scale,
            fontWeight = fontWeight,
            color = color,
            letterSpacing = if (letterSpacing.isSpecified) letterSpacing * scale else letterSpacing,
            textAlign = textAlign,
            maxLines = 1,
            softWrap = false
        )
    }
}
