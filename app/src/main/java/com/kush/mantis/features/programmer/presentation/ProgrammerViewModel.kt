package com.kush.mantis.features.programmer.presentation

import androidx.lifecycle.ViewModel
import com.kush.mantis.features.programmer.domain.BaseConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProgrammerViewModel @Inject constructor() : ViewModel() {

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
            }
            is ProgrammerEvent.OnInput -> {
                if (_inputString.value == "0") {
                    _inputString.value = event.input
                } else {
                    _inputString.update { it + event.input }
                }
                updateValueFromInput()
            }
            is ProgrammerEvent.OnDelete -> {
                if (_inputString.value.length > 1) {
                    _inputString.update { it.dropLast(1) }
                } else {
                    _inputString.value = "0"
                }
                updateValueFromInput()
            }
            is ProgrammerEvent.OnClear -> {
                _inputString.value = "0"
                _currentValue.value = 0L
            }
            is ProgrammerEvent.OnBitwiseOp -> {
                if (event.op == "NOT") {
                    _currentValue.value = _currentValue.value.inv()
                    _inputString.value = BaseConverter.convert(_currentValue.value, _activeBase.value)
                } else {
                    previousValue = _currentValue.value
                    pendingOperator = event.op
                    _inputString.value = "0"
                }
            }
            is ProgrammerEvent.OnEquals -> {
                if (previousValue != null && pendingOperator != null) {
                    val current = _currentValue.value
                    val prev = previousValue!!
                    val result = when (pendingOperator) {
                        "AND" -> prev and current
                        "OR" -> prev or current
                        "XOR" -> prev xor current
                        "<<" -> prev shl current.toInt()
                        ">>" -> prev shr current.toInt()
                        else -> current
                    }
                    _currentValue.value = result
                    _inputString.value = BaseConverter.convert(result, _activeBase.value)
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
    }
}

sealed class ProgrammerEvent {
    data class SetBase(val base: Int) : ProgrammerEvent()
    data class OnInput(val input: String) : ProgrammerEvent()
    data class OnBitwiseOp(val op: String) : ProgrammerEvent()
    object OnDelete : ProgrammerEvent()
    object OnClear : ProgrammerEvent()
    object OnEquals : ProgrammerEvent()
}
