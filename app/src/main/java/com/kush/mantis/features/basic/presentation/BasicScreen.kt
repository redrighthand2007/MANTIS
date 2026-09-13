package com.kush.mantis.features.basic.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kush.mantis.core.ui.components.CalcButton
import com.kush.mantis.core.ui.components.DisplayPanel
import com.kush.mantis.ui.theme.AccentOrange
import com.kush.mantis.ui.theme.AccentRed
import com.kush.mantis.ui.theme.MantisGreen

import com.kush.mantis.core.ui.components.TopHeader

@Composable
fun BasicScreen(
    viewModel: BasicViewModel = hiltViewModel()
) {
    val expression by viewModel.expression.collectAsState()
    val result by viewModel.result.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        DisplayPanel(
            expression = expression,
            onExpressionChange = { viewModel.onEvent(BasicCalcEvent.OnExpressionChange(it)) },
            result = result,
            swapInputOutput = true,
            modifier = Modifier.weight(0.40f)
        )
        // Keypad
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.60f)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // MANTIS Logo Box - Creative Banner
            val isDark = androidx.compose.foundation.isSystemInDarkTheme()
            val gradientStart = if (isDark) com.kush.mantis.ui.theme.MantisGreenDark else com.kush.mantis.ui.theme.MantisGreen
            val titleColor = if (isDark) androidx.compose.ui.graphics.Color.White else com.kush.mantis.ui.theme.MantisGreenDark
            val subtitleColor = if (isDark) com.kush.mantis.ui.theme.MantisGreenLight else com.kush.mantis.ui.theme.MantisGreenDark

            androidx.compose.material3.Card(
                modifier = Modifier.fillMaxWidth().weight(2f),
                elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 6.dp),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = androidx.compose.ui.graphics.Brush.linearGradient(
                                colors = listOf(
                                    gradientStart,
                                    MaterialTheme.colorScheme.surfaceVariant,
                                    gradientStart
                                )
                            )
                        ),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
                        drawCircle(
                            color = (if (isDark) com.kush.mantis.ui.theme.MantisGreen else androidx.compose.ui.graphics.Color.White).copy(alpha = 0.15f),
                            radius = size.minDimension * 1.2f,
                            center = androidx.compose.ui.geometry.Offset(size.width * 0.8f, size.height * -0.2f)
                        )
                        drawCircle(
                            color = (if (isDark) com.kush.mantis.ui.theme.MantisGreen else androidx.compose.ui.graphics.Color.White).copy(alpha = 0.1f),
                            radius = size.minDimension * 0.6f,
                            center = androidx.compose.ui.geometry.Offset(size.width * 0.1f, size.height * 1.1f)
                        )
                    }

                    Column(
                        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        androidx.compose.material3.Text(
                            text = "M A N T I S",
                            fontSize = 28.sp,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.ExtraBold,
                            letterSpacing = 10.sp,
                            color = titleColor
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        androidx.compose.material3.Text(
                            text = "PRECISION CALCULATOR",
                            fontSize = 9.sp,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                            letterSpacing = 4.sp,
                            color = subtitleColor
                        )
                    }
                }
            }

            val rowModifier = Modifier.weight(1f)

            // Row 1
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("7", Modifier.weight(1f)) { viewModel.onEvent(BasicCalcEvent.OnNumberClick("7")) }
                CalcButton("8", Modifier.weight(1f)) { viewModel.onEvent(BasicCalcEvent.OnNumberClick("8")) }
                CalcButton("9", Modifier.weight(1f)) { viewModel.onEvent(BasicCalcEvent.OnNumberClick("9")) }
                CalcButton("(", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(BasicCalcEvent.OnOperatorClick("(")) }
                CalcButton(")", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(BasicCalcEvent.OnOperatorClick(")")) }
            }
            // Row 2
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("4", Modifier.weight(1f)) { viewModel.onEvent(BasicCalcEvent.OnNumberClick("4")) }
                CalcButton("5", Modifier.weight(1f)) { viewModel.onEvent(BasicCalcEvent.OnNumberClick("5")) }
                CalcButton("6", Modifier.weight(1f)) { viewModel.onEvent(BasicCalcEvent.OnNumberClick("6")) }
                CalcButton("+", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(BasicCalcEvent.OnOperatorClick("+")) }
                CalcButton("-", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(BasicCalcEvent.OnOperatorClick("-")) }
            }
            // Row 3
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("1", Modifier.weight(1f)) { viewModel.onEvent(BasicCalcEvent.OnNumberClick("1")) }
                CalcButton("2", Modifier.weight(1f)) { viewModel.onEvent(BasicCalcEvent.OnNumberClick("2")) }
                CalcButton("3", Modifier.weight(1f)) { viewModel.onEvent(BasicCalcEvent.OnNumberClick("3")) }
                CalcButton("÷", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(BasicCalcEvent.OnOperatorClick("÷")) }
                CalcButton("×", Modifier.weight(1f), textColor = MantisGreen) { viewModel.onEvent(BasicCalcEvent.OnOperatorClick("×")) }
            }
            // Row 4
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("C", Modifier.weight(1f), textColor = AccentRed) { viewModel.onEvent(BasicCalcEvent.OnClearClick) }
                CalcButton("0", Modifier.weight(1f)) { viewModel.onEvent(BasicCalcEvent.OnNumberClick("0")) }
                CalcButton(".", Modifier.weight(1f)) { viewModel.onEvent(BasicCalcEvent.OnNumberClick(".")) }
                CalcButton("⌫", Modifier.weight(1f), textColor = AccentOrange) { viewModel.onEvent(BasicCalcEvent.OnDeleteClick) }
                CalcButton("=", Modifier.weight(1f), color = MantisGreen, textColor = Color.Black) { viewModel.onEvent(BasicCalcEvent.OnEqualsClick) }
            }
        }
    }
}
