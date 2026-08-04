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
}
