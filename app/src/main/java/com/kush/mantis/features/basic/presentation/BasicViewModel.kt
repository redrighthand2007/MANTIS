package com.kush.mantis.features.basic.presentation

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kush.mantis.core.util.CursorUtil
import com.kush.mantis.core.util.NumberFormatter
import com.kush.mantis.features.basic.domain.EvaluateExpressionUseCase
import com.kush.mantis.features.basic.domain.EvaluateExpressionUseCase.Companion.MAX_EXPRESSION_LENGTH
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasicViewModel @Inject constructor(
    private val evaluateExpressionUseCase: EvaluateExpressionUseCase,
    private val savedState: SavedStateHandle
) : ViewModel() {

    // Restored after Android kills the app in the background
    private val _expression = MutableStateFlow(savedState.get<String>(KEY_EXPRESSION).orEmpty().let { TextFieldValue(it, TextRange(it.length)) })
    val expression: StateFlow<TextFieldValue> = _expression.asStateFlow()

    private val _result = MutableStateFlow(savedState.get<String>(KEY_RESULT).orEmpty())
    val result: StateFlow<String> = _result.asStateFlow()

    // True right after "=": a digit starts a new expression, an operator continues from the result
    private var justEvaluated = false

    fun onEvent(event: BasicCalcEvent) {
        when (event) {
            is BasicCalcEvent.OnNumberClick -> {
                if (justEvaluated) setExpression(CursorUtil.clear())
                if (event.number == "." && !CursorUtil.canInsertDecimal(_expression.value)) return
                insert(event.number)
            }
            is BasicCalcEvent.OnOperatorClick -> insert(event.operator)
            is BasicCalcEvent.OnClearClick -> {
                setExpression(CursorUtil.clear())
                setResult("")
                justEvaluated = false
            }
            is BasicCalcEvent.OnDeleteClick -> {
                if (_expression.value.text.isNotEmpty()) {
                    setExpression(CursorUtil.deleteText(_expression.value))
                }
                justEvaluated = false
            }
            is BasicCalcEvent.OnEqualsClick -> evaluate()
            is BasicCalcEvent.OnExpressionChange -> {
                // Pasted text beyond the limit is rejected rather than risking a slow calculation
                if (event.value.text.length > MAX_EXPRESSION_LENGTH) return
                val previous = _expression.value
                setExpression(event.value)
                // The text field also reports no-op updates; only real edits or cursor moves count
                if (event.value.text != previous.text || event.value.selection != previous.selection) {
                    justEvaluated = false
                }
            }
        }
    }

    private fun insert(input: String) {
        val updated = CursorUtil.insertText(_expression.value, input)
        if (updated.text.length > MAX_EXPRESSION_LENGTH) return
        setExpression(updated)
        justEvaluated = false
    }

    private fun evaluate() {
        val text = _expression.value.text
        if (text.isBlank()) return
        viewModelScope.launch {
            val value = evaluateExpressionUseCase.evaluateAsync(text)
            // Ignore a stale answer if the user kept editing while it was calculating
            if (_expression.value.text != text) return@launch
            val display = value?.let { NumberFormatter.formatResult(it) }
            val reusable = value?.let { NumberFormatter.toExpression(it) }
            if (display == null || reusable == null) {
                setResult(EvaluateExpressionUseCase.ERROR)
                return@launch
            }
            setResult(display)
            setExpression(TextFieldValue(reusable, TextRange(reusable.length)))
            justEvaluated = true
        }
    }

    private fun setExpression(value: TextFieldValue) {
        _expression.value = value
        savedState[KEY_EXPRESSION] = value.text
    }

    private fun setResult(value: String) {
        _result.value = value
        savedState[KEY_RESULT] = value
    }

    private companion object {
        const val KEY_EXPRESSION = "expression"
        const val KEY_RESULT = "result"
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
