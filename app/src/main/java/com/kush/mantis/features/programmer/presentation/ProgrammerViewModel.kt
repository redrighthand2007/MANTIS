package com.kush.mantis.features.programmer.presentation

import androidx.lifecycle.ViewModel
import com.kush.mantis.features.programmer.domain.BaseConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.TextRange
import javax.inject.Inject
import kotlin.math.max

@HiltViewModel
class ProgrammerViewModel @Inject constructor() : ViewModel() {

    private val _expression = MutableStateFlow(TextFieldValue(""))
    val expression: StateFlow<TextFieldValue> = _expression.asStateFlow()

    private val _result = MutableStateFlow("")
    val result: StateFlow<String> = _result.asStateFlow()

    private val _currentValue = MutableStateFlow(0L)
    val currentValue: StateFlow<Long> = _currentValue.asStateFlow()

    private val _activeBase = MutableStateFlow(10)
    val activeBase: StateFlow<Int> = _activeBase.asStateFlow()

    private val _inputString = MutableStateFlow("0")
    val inputString: StateFlow<String> = _inputString.asStateFlow()

    private var previousValue: Long? = null
    private var pendingOperator: String? = null

    fun onEvent(event: ProgrammerEvent) {
        when (event) {
            is ProgrammerEvent.SetBase -> {
                _activeBase.value = event.base
                _inputString.value = BaseConverter.convert(_currentValue.value, event.base)
                // Reset expression to match new base if no pending operator to keep it clean
                if (pendingOperator == null) {
                    _expression.value = TextFieldValue(_inputString.value, TextRange(_inputString.value.length))
                }
                evaluateLive()
            }
            is ProgrammerEvent.OnExpressionChange -> {
                _expression.value = event.value
            }
            is ProgrammerEvent.OnInput -> {
                if (_inputString.value == "0" && event.input != "0") {
                    _inputString.value = event.input
                } else {
                    _inputString.update { it + event.input }
                }
                
                if (_expression.value.text == "0") {
                    _expression.value = TextFieldValue(event.input, TextRange(event.input.length))
                } else {
                    _expression.value = TextFieldValue(_expression.value.text + event.input, TextRange(_expression.value.text.length + event.input.length))
                }
                updateValueFromInput()
            }
            is ProgrammerEvent.OnDelete -> {
                if (_inputString.value.length > 1) {
                    _inputString.update { it.dropLast(1) }
                } else {
                    _inputString.value = "0"
                }
                if (_expression.value.text.isNotEmpty()) {
                    _expression.value = TextFieldValue(_expression.value.text.dropLast(1), TextRange(max(0, _expression.value.text.length - 1)))
                }
                updateValueFromInput()
            }
            is ProgrammerEvent.OnClear -> {
                _inputString.value = "0"
                _currentValue.value = 0L
                _expression.value = TextFieldValue("")
                _result.value = ""
                previousValue = null
                pendingOperator = null
            }
            is ProgrammerEvent.OnBitwiseOp -> {
                if (event.op == "NOT") {
                    _currentValue.value = _currentValue.value.inv()
                    val res = BaseConverter.convert(_currentValue.value, _activeBase.value)
                    _inputString.value = res
                    _expression.value = TextFieldValue(res, TextRange(res.length))
                } else {
                    previousValue = _currentValue.value
                    pendingOperator = event.op
                    _inputString.value = "0"
                    _expression.value = TextFieldValue(_expression.value.text + " ${event.op} ", TextRange(_expression.value.text.length + event.op.length + 2))
                }
            }
            is ProgrammerEvent.OnEquals -> {
                if (previousValue != null && pendingOperator != null) {
                    val current = _currentValue.value
                    val prev = previousValue!!
                    val computed = when (pendingOperator) {
                        "AND" -> prev and current
                        "OR" -> prev or current
                        "XOR" -> prev xor current
                        "<<" -> prev shl current.toInt()
                        ">>" -> prev shr current.toInt()
                        else -> current
                    }
                    _currentValue.value = computed
                    val resultStr = BaseConverter.convert(computed, _activeBase.value)
                    _inputString.value = resultStr
                    
                    _expression.value = TextFieldValue(resultStr, TextRange(resultStr.length))
                    _result.value = ""
                    
                    previousValue = null
                    pendingOperator = null
                }
            }
        }
    }

    private fun updateValueFromInput() {
        val parsed = BaseConverter.parse(_inputString.value, _activeBase.value)
        if (parsed != null) {
            _currentValue.value = parsed
        }
        evaluateLive()
    }

    private fun evaluateLive() {
        if (previousValue != null && pendingOperator != null) {
            val current = _currentValue.value
            val prev = previousValue!!
            val computed = when (pendingOperator) {
                "AND" -> prev and current
                "OR" -> prev or current
                "XOR" -> prev xor current
                "<<" -> prev shl current.toInt()
                ">>" -> prev shr current.toInt()
                else -> current
            }
            _result.value = BaseConverter.convert(computed, _activeBase.value)
        } else {
            _result.value = BaseConverter.convert(_currentValue.value, _activeBase.value)
        }
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
