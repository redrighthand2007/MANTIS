package com.kush.mantis.features.basic.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.kush.mantis.core.ui.components.AutoSizeText
import com.kush.mantis.core.ui.components.CalculatorLayout
import com.kush.mantis.core.ui.components.ClearKey
import com.kush.mantis.core.ui.components.DeleteKey
import com.kush.mantis.core.ui.components.DisplayPanel
import com.kush.mantis.core.ui.components.EqualsKey
import com.kush.mantis.core.ui.components.Key
import com.kush.mantis.core.ui.components.KeypadRow
import com.kush.mantis.core.ui.components.OperatorKey
import com.kush.mantis.core.ui.components.isCompactHeight
import com.kush.mantis.ui.theme.MantisGreen
import com.kush.mantis.ui.theme.MantisGreenDark
import com.kush.mantis.ui.theme.MantisGreenLight

@Composable
fun BasicScreen(viewModel: BasicViewModel = hiltViewModel()) {
    val expression by viewModel.expression.collectAsState()
    val result by viewModel.result.collectAsState()
    val number = { digit: String -> viewModel.onEvent(BasicCalcEvent.OnNumberClick(digit)) }
    val operator = { symbol: String -> viewModel.onEvent(BasicCalcEvent.OnOperatorClick(symbol)) }

    CalculatorLayout(
        display = { modifier ->
            DisplayPanel(
                expression = expression,
                onExpressionChange = { viewModel.onEvent(BasicCalcEvent.OnExpressionChange(it)) },
                result = result,
                modifier = modifier
            )
        }
    ) {
        // Hidden in split-screen to leave room for the keys
        if (!isCompactHeight()) {
            MantisBanner(Modifier.fillMaxWidth().weight(2f))
        }
        KeypadRow {
            listOf("7", "8", "9").forEach { Key(it) { number(it) } }
            OperatorKey("(") { operator("(") }
            OperatorKey(")") { operator(")") }
        }
        KeypadRow {
            listOf("4", "5", "6").forEach { Key(it) { number(it) } }
            OperatorKey("+") { operator("+") }
            OperatorKey("-") { operator("-") }
        }
        KeypadRow {
            listOf("1", "2", "3").forEach { Key(it) { number(it) } }
            OperatorKey("÷") { operator("÷") }
            OperatorKey("×") { operator("×") }
        }
        KeypadRow {
            ClearKey { viewModel.onEvent(BasicCalcEvent.OnClearClick) }
            Key("0") { number("0") }
            Key(".") { number(".") }
            DeleteKey { viewModel.onEvent(BasicCalcEvent.OnDeleteClick) }
            EqualsKey { viewModel.onEvent(BasicCalcEvent.OnEqualsClick) }
        }
    }
}

/** Branded gradient card with soft decorative circles, tuned for light and dark themes. */
@Composable
private fun MantisBanner(modifier: Modifier = Modifier) {
    val isDark = isSystemInDarkTheme()
    val gradient = if (isDark) listOf(Color(0xFF053621), MantisGreenDark) else listOf(Color(0xFFC4F9D9), MantisGreenLight)
    val titleColor = if (isDark) Color.White else MantisGreenDark
    val subtitleColor = (if (isDark) MantisGreen else MantisGreenDark).copy(alpha = 0.8f)

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.linearGradient(gradient)),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(
                    color = Color.White.copy(alpha = if (isDark) 0.03f else 0.25f),
                    radius = size.minDimension * 1.2f,
                    center = Offset(size.width * 0.8f, size.height * -0.2f)
                )
                drawCircle(
                    color = Color.White.copy(alpha = if (isDark) 0.02f else 0.20f),
                    radius = size.minDimension * 0.6f,
                    center = Offset(size.width * 0.1f, size.height * 1.1f)
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Auto-sized so the letter-spaced title never wraps at large system font sizes
                AutoSizeText(
                    text = "M A N T I S",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 10.sp,
                    color = titleColor,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(2.dp))
                AutoSizeText(
                    text = "PRECISION CALCULATOR",
                    fontSize = 9.sp,
                    minFontSize = 6.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 4.sp,
                    color = subtitleColor,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                )
            }
        }
    }
}
