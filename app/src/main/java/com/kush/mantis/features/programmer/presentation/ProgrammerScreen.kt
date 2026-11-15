package com.kush.mantis.features.programmer.presentation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.kush.mantis.core.ui.components.CalculatorLayout
import com.kush.mantis.core.ui.components.ClearKey
import com.kush.mantis.core.ui.components.DeleteKey
import com.kush.mantis.core.ui.components.DisplayPanel
import com.kush.mantis.core.ui.components.EqualsKey
import com.kush.mantis.core.ui.components.Key
import com.kush.mantis.core.ui.components.KeypadRow
import com.kush.mantis.core.ui.components.KeypadSlot
import com.kush.mantis.core.ui.components.OperatorKey
import com.kush.mantis.core.ui.components.PillSelector
import com.kush.mantis.features.programmer.domain.BaseConverter

private val bases = listOf("HEX" to 16, "DEC" to 10, "OCT" to 8, "BIN" to 2)

@Composable
fun ProgrammerScreen(viewModel: ProgrammerViewModel = hiltViewModel()) {
    val activeBase by viewModel.activeBase.collectAsState()
    val expression by viewModel.expression.collectAsState()
    val result by viewModel.result.collectAsState()
    val operator = { op: String -> viewModel.onEvent(ProgrammerEvent.OnBitwiseOp(op)) }

    CalculatorLayout(
        display = { modifier ->
            // Read-only: keypad events are the only input, so the display can't drift from the calculation
            DisplayPanel(
                expression = expression,
                onExpressionChange = { viewModel.onEvent(ProgrammerEvent.OnExpressionChange(it)) },
                result = result,
                readOnly = true,
                modifier = modifier
            )
        }
    ) {
        KeypadSlot {
            PillSelector(
                items = bases.map { it.first },
                selectedItem = bases.first { it.second == activeBase }.first,
                onItemSelected = { name -> viewModel.onEvent(ProgrammerEvent.SetBase(bases.first { it.first == name }.second)) },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
            )
        }
        KeypadRow {
            listOf("A", "B", "C", "D", "E", "F").forEach { DigitKey(it, activeBase, viewModel) }
        }
        KeypadRow {
            listOf("7", "8", "9").forEach { DigitKey(it, activeBase, viewModel) }
            OperatorKey("<<") { operator("<<") }
            OperatorKey(">>") { operator(">>") }
        }
        KeypadRow {
            listOf("4", "5", "6").forEach { DigitKey(it, activeBase, viewModel) }
            OperatorKey("XOR") { operator("XOR") }
            OperatorKey("NOT") { operator("NOT") }
        }
        KeypadRow {
            listOf("1", "2", "3").forEach { DigitKey(it, activeBase, viewModel) }
            OperatorKey("AND") { operator("AND") }
            OperatorKey("OR") { operator("OR") }
        }
        KeypadRow {
            ClearKey { viewModel.onEvent(ProgrammerEvent.OnClear) }
            DigitKey("0", activeBase, viewModel)
            Key(".", enabled = false) {}
            DeleteKey { viewModel.onEvent(ProgrammerEvent.OnDelete) }
            EqualsKey { viewModel.onEvent(ProgrammerEvent.OnEquals) }
        }
    }
}

/** Digits that are not valid in the active base (e.g. 2-9 in BIN) are shown disabled. */
@Composable
private fun RowScope.DigitKey(digit: String, activeBase: Int, viewModel: ProgrammerViewModel) {
    Key(digit, enabled = BaseConverter.isValidDigit(digit[0], activeBase)) {
        viewModel.onEvent(ProgrammerEvent.OnInput(digit))
    }
}
