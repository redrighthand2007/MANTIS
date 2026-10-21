package com.kush.mantis.features.programmer.presentation

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.kush.mantis.features.programmer.domain.BaseConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProgrammerViewModel @Inject constructor() : ViewModel() {

    private val _expression = MutableStateFlow(TextFieldValue("0", TextRange(1)))
    val expression: StateFlow<TextFieldValue> = _expression.asStateFlow()

    private val _result = MutableStateFlow("")
    val result: StateFlow<String> = _result.asStateFlow()

    private val _currentValue = MutableStateFlow(0L)
    val currentValue: StateFlow<Long> = _currentValue.asStateFlow()

    private val _activeBase = MutableStateFlow(10)
    val activeBase: StateFlow<Int> = _activeBase.asStateFlow()

    private val _inputString = MutableStateFlow("0")
    val inputString: StateFlow<String> = _inputString.asStateFlow()

    // Left-hand side and operator of an unfinished binary operation, e.g. "12 AND"
    private var accumulator: Long? = null
    private var pendingOperator: String? = null

    // The operand being edited; operandTyped is false right after an operator (nothing entered yet)
    private var operand = 0L
    private var operandText = "0"
    private var operandTyped = false

    // After "=", NOT or an operator, the next digit starts a new number instead of appending
    private var replaceOnNextDigit = false
    private var resultValue: Long? = null

    fun onEvent(event: ProgrammerEvent) {
        when (event) {
            is ProgrammerEvent.SetBase -> {
                if (event.base !in BaseConverter.SUPPORTED_BASES) return
                _activeBase.value = event.base
                operandText = format(operand)
            }
            is ProgrammerEvent.OnInput -> appendDigit(event.input)
            is ProgrammerEvent.OnDelete -> delete()
            is ProgrammerEvent.OnClear -> {
                accumulator = null
                pendingOperator = null
                operand = 0L
                operandText = "0"
                operandTyped = false
                replaceOnNextDigit = false
                resultValue = null
            }
            is ProgrammerEvent.OnBitwiseOp -> {
                if (event.op == "NOT") applyNot() else applyOperator(event.op)
            }
            is ProgrammerEvent.OnEquals -> equals()
            // The display is read-only; keypad events are the only source of input
            is ProgrammerEvent.OnExpressionChange -> return
        }
        refresh()
    }

    private fun appendDigit(digit: String) {
        val base = _activeBase.value
        if (digit.length != 1 || !BaseConverter.isValidDigit(digit[0], base)) return

        val current = if (replaceOnNextDigit || operandText == "0") "" else operandText
        val candidate = (current + digit.uppercase()).trimStart('0').ifEmpty { "0" }
        // Ignore digits that would overflow 64 bits instead of silently keeping a stale value
        val parsed = BaseConverter.parse(candidate, base) ?: return

        operand = parsed
        operandText = candidate
        operandTyped = true
        replaceOnNextDigit = false
    }

    private fun delete() {
        when {
            operandTyped && !replaceOnNextDigit -> {
                operandText = operandText.dropLast(1).ifEmpty { "0" }
                operand = BaseConverter.parse(operandText, _activeBase.value) ?: 0L
                if (operandText == "0") operandTyped = false
            }
            pendingOperator != null && !operandTyped -> {
                // Nothing typed after the operator: remove the operator and resume editing the left side
                operand = accumulator ?: 0L
                operandText = format(operand)
                operandTyped = true
                accumulator = null
                pendingOperator = null
                replaceOnNextDigit = false
            }
            else -> {
                operand = 0L
                operandText = "0"
                operandTyped = false
                replaceOnNextDigit = false
            }
        }
    }

    private fun applyOperator(op: String) {
        val pending = pendingOperator
        if (pending == null) {
            accumulator = operand
        } else if (operandTyped) {
            // Chain: "5 AND 3 OR" evaluates "5 AND 3" before applying OR
            accumulator = compute(accumulator ?: 0L, pending, operand)
        }
        pendingOperator = op
        operand = 0L
        operandText = "0"
        operandTyped = false
        replaceOnNextDigit = true
    }

    private fun applyNot() {
        if (pendingOperator != null && !operandTyped) return
        operand = operand.inv()
        operandText = format(operand)
        operandTyped = true
        replaceOnNextDigit = true
    }

    private fun equals() {
        val pending = pendingOperator
        val value = if (pending != null) {
            val left = accumulator ?: 0L
            compute(left, pending, if (operandTyped) operand else left)
        } else {
            operand
        }
        resultValue = value
        operand = value
        operandText = format(value)
        operandTyped = true
        accumulator = null
        pendingOperator = null
        replaceOnNextDigit = true
    }

    private fun compute(left: Long, op: String, right: Long): Long {
        // Kotlin shifts only use the low 6 bits (1 shl 64 == 1), so clamp the shift distance explicitly
        val shift = right.coerceIn(0L, 64L).toInt()
        return when (op) {
            "AND" -> left and right
            "OR" -> left or right
            "XOR" -> left xor right
            "<<" -> if (shift >= 64) 0L else left shl shift
            ">>" -> if (shift >= 64) (if (left < 0) -1L else 0L) else left shr shift
            else -> right
        }
    }

    private fun format(value: Long): String = BaseConverter.convert(value, _activeBase.value)

    private fun refresh() {
        val op = pendingOperator
        val text = if (op != null) {
            val left = format(accumulator ?: 0L)
            if (operandTyped) "$left $op $operandText" else "$left $op"
        } else {
            operandText
        }
        _expression.value = TextFieldValue(text, TextRange(text.length))
        _inputString.value = operandText
        _currentValue.value = operand
        _result.value = resultValue?.let { format(it) } ?: ""
    }
}

sealed class ProgrammerEvent {
    data class SetBase(val base: Int) : ProgrammerEvent()
    data class OnInput(val input: String) : ProgrammerEvent()
    data class OnExpressionChange(val value: TextFieldValue) : ProgrammerEvent()
    data class OnBitwiseOp(val op: String) : ProgrammerEvent()
    object OnDelete : ProgrammerEvent()
    object OnClear : ProgrammerEvent()
    object OnEquals : ProgrammerEvent()
}
