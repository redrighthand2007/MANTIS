package com.kush.mantis.features.basic.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kush.mantis.features.basic.domain.EvaluateExpressionUseCase
import com.kush.mantis.features.history.data.CalculationHistory
import com.kush.mantis.features.history.data.HistoryDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasicViewModel @Inject constructor(
    private val evaluateExpressionUseCase: EvaluateExpressionUseCase,
    private val historyDao: HistoryDao
) : ViewModel() {

    private val _expression = MutableStateFlow("")
    val expression: StateFlow<String> = _expression.asStateFlow()

    private val _result = MutableStateFlow("")
    val result: StateFlow<String> = _result.asStateFlow()

    fun onEvent(event: BasicCalcEvent) {
        when (event) {
            is BasicCalcEvent.OnNumberClick -> {
                _expression.update { it + event.number }
                evaluateLive()
            }
            is BasicCalcEvent.OnOperatorClick -> {
                _expression.update { it + event.operator }
            }
            is BasicCalcEvent.OnClearClick -> {
                _expression.value = ""
                _result.value = ""
            }
            is BasicCalcEvent.OnDeleteClick -> {
                if (_expression.value.isNotEmpty()) {
                    _expression.update { it.dropLast(1) }
                    evaluateLive()
                }
            }
            is BasicCalcEvent.OnEqualsClick -> {
                val finalResult = evaluateExpressionUseCase(_expression.value)
                if (finalResult != "Error" && finalResult.isNotEmpty()) {
                    _result.value = finalResult
                    
                    viewModelScope.launch {
                        historyDao.insertHistory(
                            CalculationHistory(
                                expression = _expression.value,
                                result = finalResult,
                                timestamp = System.currentTimeMillis()
                            )
                        )
                    }
                }
            }
        }
    }

    private fun evaluateLive() {
        val currentResult = evaluateExpressionUseCase(_expression.value)
        if (currentResult != "Error") {
            _result.value = currentResult
        }
    }
}

sealed class BasicCalcEvent {
    data class OnNumberClick(val number: String) : BasicCalcEvent()
    data class OnOperatorClick(val operator: String) : BasicCalcEvent()
    object OnClearClick : BasicCalcEvent()
    object OnDeleteClick : BasicCalcEvent()
    object OnEqualsClick : BasicCalcEvent()
}
