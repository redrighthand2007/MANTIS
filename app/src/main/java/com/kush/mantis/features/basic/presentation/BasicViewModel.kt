package com.kush.mantis.features.basic.presentation

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.kush.mantis.core.util.CursorUtil
import com.kush.mantis.core.util.NumberFormatter
import com.kush.mantis.features.basic.domain.EvaluateExpressionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class BasicViewModel @Inject constructor(
    private val evaluateExpressionUseCase: EvaluateExpressionUseCase
) : ViewModel() {

    private val _expression = MutableStateFlow(TextFieldValue(""))
    val expression: StateFlow<TextFieldValue> = _expression.asStateFlow()

    private val _result = MutableStateFlow("")
    val result: StateFlow<String> = _result.asStateFlow()

    // True right after "=": a digit starts a new expression, an operator continues from the result
    private var justEvaluated = false

    fun onEvent(event: BasicCalcEvent) {
        when (event) {
            is BasicCalcEvent.OnNumberClick -> {
                if (justEvaluated) _expression.value = CursorUtil.clear()
                if (event.number == "." && !CursorUtil.canInsertDecimal(_expression.value)) return
                _expression.value = CursorUtil.insertText(_expression.value, event.number)
                justEvaluated = false
            }
            is BasicCalcEvent.OnOperatorClick -> {
                _expression.value = CursorUtil.insertText(_expression.value, event.operator)
                justEvaluated = false
            }
            is BasicCalcEvent.OnClearClick -> {
                _expression.value = CursorUtil.clear()
                _result.value = ""
                justEvaluated = false
            }
            is BasicCalcEvent.OnDeleteClick -> {
                if (_expression.value.text.isNotEmpty()) {
                    _expression.value = CursorUtil.deleteText(_expression.value)
                }
                justEvaluated = false
            }
            is BasicCalcEvent.OnEqualsClick -> {
                val text = _expression.value.text
                if (text.isBlank()) return
                val value = evaluateExpressionUseCase.evaluate(text)
                val display = value?.let { NumberFormatter.formatResult(it) }
                val reusable = value?.let { NumberFormatter.toExpression(it) }
                if (display == null || reusable == null) {
                    _result.value = EvaluateExpressionUseCase.ERROR
                    return
                }
                _result.value = display
                _expression.value = TextFieldValue(reusable, TextRange(reusable.length))
                justEvaluated = true
            }
            is BasicCalcEvent.OnExpressionChange -> {
                val previous = _expression.value
                _expression.value = event.value
                // The text field also reports no-op updates; only real edits or cursor moves count
                if (event.value.text != previous.text || event.value.selection != previous.selection) {
                    justEvaluated = false
                }
            }
        }
    }
}

sealed class BasicCalcEvent {
    data class OnNumberClick(val number: String) : BasicCalcEvent()
    data class OnOperatorClick(val operator: String) : BasicCalcEvent()
    data class OnExpressionChange(val value: TextFieldValue) : BasicCalcEvent()
    object OnClearClick : BasicCalcEvent()
    object OnDeleteClick : BasicCalcEvent()
    object OnEqualsClick : BasicCalcEvent()
}
