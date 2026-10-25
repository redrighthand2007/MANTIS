package com.kush.mantis.features.scientific.presentation

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kush.mantis.core.util.CursorUtil
import com.kush.mantis.core.util.NumberFormatter
import com.kush.mantis.features.basic.domain.EvaluateExpressionUseCase
import com.kush.mantis.features.basic.domain.EvaluateExpressionUseCase.Companion.MAX_EXPRESSION_LENGTH
import com.kush.mantis.features.scientific.domain.ScientificFunctions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScientificViewModel @Inject constructor(
    private val evaluateExpressionUseCase: EvaluateExpressionUseCase,
    private val savedState: SavedStateHandle
) : ViewModel() {

    // Restored after Android kills the app in the background
    private val _expression = MutableStateFlow(savedState.get<String>(KEY_EXPRESSION).orEmpty().let { TextFieldValue(it, TextRange(it.length)) })
    val expression: StateFlow<TextFieldValue> = _expression.asStateFlow()

    private val _result = MutableStateFlow(savedState.get<String>(KEY_RESULT).orEmpty())
    val result: StateFlow<String> = _result.asStateFlow()

    private val _isSecondMode = MutableStateFlow(savedState.get<Boolean>(KEY_SECOND) ?: false)
    val isSecondMode: StateFlow<Boolean> = _isSecondMode.asStateFlow()

    private val _isDegreeMode = MutableStateFlow(savedState.get<Boolean>(KEY_DEGREES) ?: true)
    val isDegreeMode: StateFlow<Boolean> = _isDegreeMode.asStateFlow()

    // True right after "=": a number or function starts a new expression, an operator continues from the result
    private var justEvaluated = false

    fun onEvent(event: ScientificEvent) {
        when (event) {
            is ScientificEvent.OnInput -> {
                val mappedInput = ScientificFunctions.functionMap[event.input] ?: event.input
                if (justEvaluated && !ScientificFunctions.continuesExpression(mappedInput)) {
                    setExpression(CursorUtil.clear())
                }
                if (mappedInput == "." && !CursorUtil.canInsertDecimal(_expression.value)) return
                val updated = CursorUtil.insertText(_expression.value, mappedInput)
                if (updated.text.length > MAX_EXPRESSION_LENGTH) return
                setExpression(updated)
                justEvaluated = false
            }
            is ScientificEvent.OnClear -> {
                setExpression(CursorUtil.clear())
                setResult("")
                justEvaluated = false
            }
            is ScientificEvent.OnDelete -> {
                if (_expression.value.text.isNotEmpty()) {
                    setExpression(CursorUtil.deleteText(_expression.value))
                }
                justEvaluated = false
            }
            is ScientificEvent.OnEquals -> evaluate()
            is ScientificEvent.OnExpressionChange -> {
                // Pasted text beyond the limit is rejected rather than risking a slow calculation
                if (event.value.text.length > MAX_EXPRESSION_LENGTH) return
                val previous = _expression.value
                setExpression(event.value)
                // The text field also reports no-op updates; only real edits or cursor moves count
                if (event.value.text != previous.text || event.value.selection != previous.selection) {
                    justEvaluated = false
                }
            }
            is ScientificEvent.ToggleSecondMode -> {
                _isSecondMode.value = !_isSecondMode.value
                savedState[KEY_SECOND] = _isSecondMode.value
            }
            is ScientificEvent.ToggleAngleMode -> {
                _isDegreeMode.value = !_isDegreeMode.value
                savedState[KEY_DEGREES] = _isDegreeMode.value
            }
        }
    }

    private fun evaluate() {
        val text = _expression.value.text
        if (text.isBlank()) return
        val degrees = _isDegreeMode.value
        viewModelScope.launch {
            val value = evaluateExpressionUseCase.evaluateAsync(text, degrees)
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
        const val KEY_SECOND = "secondMode"
        const val KEY_DEGREES = "degreeMode"
    }
}

sealed class ScientificEvent {
    data class OnInput(val input: String) : ScientificEvent()
    data class OnExpressionChange(val value: TextFieldValue) : ScientificEvent()
    object OnClear : ScientificEvent()
    object OnDelete : ScientificEvent()
    object OnEquals : ScientificEvent()
    object ToggleSecondMode : ScientificEvent()
    object ToggleAngleMode : ScientificEvent()
}
