package com.kush.mantis.features.basic.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kush.mantis.features.basic.domain.EvaluateExpressionUseCase
import com.kush.mantis.features.history.data.CalculationHistory
import com.kush.mantis.features.history.domain.InsertHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import androidx.compose.ui.text.input.TextFieldValue
import javax.inject.Inject

@HiltViewModel
class BasicViewModel @Inject constructor(
    private val evaluateExpressionUseCase: EvaluateExpressionUseCase,
    private val insertHistoryUseCase: InsertHistoryUseCase
) : ViewModel() {

    private val _expression = MutableStateFlow(TextFieldValue(""))
    val expression: StateFlow<TextFieldValue> = _expression.asStateFlow()

    private val _result = MutableStateFlow("")
    val result: StateFlow<String> = _result.asStateFlow()

    fun onEvent(event: BasicCalcEvent) {
        when (event) {
            is BasicCalcEvent.OnNumberClick -> {
                _expression.value = com.kush.mantis.core.util.CursorUtil.insertText(_expression.value, event.number)
                evaluateLive()
            }
            is BasicCalcEvent.OnOperatorClick -> {
                _expression.value = com.kush.mantis.core.util.CursorUtil.insertText(_expression.value, event.operator)
            }
            is BasicCalcEvent.OnClearClick -> {
                _expression.value = com.kush.mantis.core.util.CursorUtil.clear()
                _result.value = ""
            }
            is BasicCalcEvent.OnDeleteClick -> {
                if (_expression.value.text.isNotEmpty()) {
                    _expression.value = com.kush.mantis.core.util.CursorUtil.deleteText(_expression.value)
                    evaluateLive()
                }
            }
            is BasicCalcEvent.OnEqualsClick -> {
                val finalResult = evaluateExpressionUseCase(_expression.value.text)
                if (finalResult.isNotEmpty()) {
                    viewModelScope.launch {
                        insertHistoryUseCase(
                            CalculationHistory(
                                expression = _expression.value.text,
                                result = finalResult,
                                mode = "Basic"
                            )
                        )
                    }
                    _expression.value = TextFieldValue(finalResult, androidx.compose.ui.text.TextRange(finalResult.length))
                    _result.value = ""
                }
            }
            is BasicCalcEvent.OnExpressionChange -> {
                _expression.value = event.value
                evaluateLive()
            }
        }
    }

    private fun evaluateLive() {
        val currentResult = evaluateExpressionUseCase(_expression.value.text)
        if (currentResult != "NaN") {
            _result.value = currentResult
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
