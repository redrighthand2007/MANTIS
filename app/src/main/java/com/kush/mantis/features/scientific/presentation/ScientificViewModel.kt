package com.kush.mantis.features.scientific.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kush.mantis.features.basic.domain.EvaluateExpressionUseCase
import com.kush.mantis.features.history.data.CalculationHistory
import com.kush.mantis.features.history.domain.InsertHistoryUseCase
import com.kush.mantis.features.scientific.domain.ScientificFunctions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import androidx.compose.ui.text.input.TextFieldValue
import javax.inject.Inject

@HiltViewModel
class ScientificViewModel @Inject constructor(
    private val evaluateExpressionUseCase: EvaluateExpressionUseCase,
    private val insertHistoryUseCase: InsertHistoryUseCase
) : ViewModel() {

    private val _expression = MutableStateFlow(TextFieldValue(""))
    val expression: StateFlow<TextFieldValue> = _expression.asStateFlow()

    private val _result = MutableStateFlow("")
    val result: StateFlow<String> = _result.asStateFlow()

    private val _isSecondMode = MutableStateFlow(false)
    val isSecondMode: StateFlow<Boolean> = _isSecondMode.asStateFlow()

    private val _isDegreeMode = MutableStateFlow(true)
    val isDegreeMode: StateFlow<Boolean> = _isDegreeMode.asStateFlow()

    fun onEvent(event: ScientificEvent) {
        when (event) {
            is ScientificEvent.OnInput -> {
                val mappedInput = ScientificFunctions.functionMap[event.input] ?: event.input
                _expression.value = com.kush.mantis.core.util.CursorUtil.insertText(_expression.value, mappedInput)
                evaluateLive()
            }
            is ScientificEvent.OnClear -> {
                _expression.value = com.kush.mantis.core.util.CursorUtil.clear()
                _result.value = ""
            }
            is ScientificEvent.OnDelete -> {
                if (_expression.value.text.isNotEmpty()) {
                    _expression.value = com.kush.mantis.core.util.CursorUtil.deleteText(_expression.value)
                    evaluateLive()
                }
            }
            is ScientificEvent.OnEquals -> {
                val finalResult = evaluateExpressionUseCase(_expression.value.text, _isDegreeMode.value)
                if (finalResult.isNotEmpty()) {
                    viewModelScope.launch {
                        insertHistoryUseCase(
                            CalculationHistory(
                                expression = _expression.value.text,
                                result = finalResult,
                                mode = "Scientific"
                            )
                        )
                    }
                    _expression.value = TextFieldValue(finalResult, androidx.compose.ui.text.TextRange(finalResult.length))
                    _result.value = ""
                }
            }
            is ScientificEvent.OnExpressionChange -> {
                _expression.value = event.value
                evaluateLive()
            }
            is ScientificEvent.ToggleSecondMode -> {
                _isSecondMode.update { !it }
            }
            is ScientificEvent.ToggleAngleMode -> {
                _isDegreeMode.update { !it }
                evaluateLive()
            }
        }
    }

    private fun evaluateLive() {
        val currentResult = evaluateExpressionUseCase(_expression.value.text, _isDegreeMode.value)
        if (currentResult != "NaN") {
            _result.value = currentResult
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
