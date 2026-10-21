package com.kush.mantis.features.scientific.presentation

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.kush.mantis.core.util.CursorUtil
import com.kush.mantis.core.util.NumberFormatter
import com.kush.mantis.features.basic.domain.EvaluateExpressionUseCase
import com.kush.mantis.features.scientific.domain.ScientificFunctions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ScientificViewModel @Inject constructor(
    private val evaluateExpressionUseCase: EvaluateExpressionUseCase
) : ViewModel() {

    private val _expression = MutableStateFlow(TextFieldValue(""))
    val expression: StateFlow<TextFieldValue> = _expression.asStateFlow()

    private val _result = MutableStateFlow("")
    val result: StateFlow<String> = _result.asStateFlow()

    private val _isSecondMode = MutableStateFlow(false)
    val isSecondMode: StateFlow<Boolean> = _isSecondMode.asStateFlow()

    private val _isDegreeMode = MutableStateFlow(true)
    val isDegreeMode: StateFlow<Boolean> = _isDegreeMode.asStateFlow()

    // True right after "=": a number or function starts a new expression, an operator continues from the result
    private var justEvaluated = false

    fun onEvent(event: ScientificEvent) {
        when (event) {
            is ScientificEvent.OnInput -> {
                val mappedInput = ScientificFunctions.functionMap[event.input] ?: event.input
                if (justEvaluated && !ScientificFunctions.continuesExpression(mappedInput)) {
                    _expression.value = CursorUtil.clear()
                }
                if (mappedInput == "." && !CursorUtil.canInsertDecimal(_expression.value)) return
                _expression.value = CursorUtil.insertText(_expression.value, mappedInput)
                justEvaluated = false
            }
            is ScientificEvent.OnClear -> {
                _expression.value = CursorUtil.clear()
                _result.value = ""
                justEvaluated = false
            }
            is ScientificEvent.OnDelete -> {
                if (_expression.value.text.isNotEmpty()) {
                    _expression.value = CursorUtil.deleteText(_expression.value)
                }
                justEvaluated = false
            }
            is ScientificEvent.OnEquals -> {
                val text = _expression.value.text
                if (text.isBlank()) return
                val value = evaluateExpressionUseCase.evaluate(text, _isDegreeMode.value)
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
            is ScientificEvent.OnExpressionChange -> {
                val previous = _expression.value
                _expression.value = event.value
                // The text field also reports no-op updates; only real edits or cursor moves count
                if (event.value.text != previous.text || event.value.selection != previous.selection) {
                    justEvaluated = false
                }
            }
            is ScientificEvent.ToggleSecondMode -> {
                _isSecondMode.update { !it }
            }
            is ScientificEvent.ToggleAngleMode -> {
                _isDegreeMode.update { !it }
            }
        }
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
