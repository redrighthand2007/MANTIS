package com.kush.mantis.features.converter.presentation

import androidx.lifecycle.ViewModel
import com.kush.mantis.features.converter.domain.ConvertUnitUseCase
import com.kush.mantis.features.converter.domain.UnitDefinition
import com.kush.mantis.features.converter.domain.UnitDefinitions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ConverterViewModel @Inject constructor(
    private val convertUnitUseCase: ConvertUnitUseCase
) : ViewModel() {

    private val _category = MutableStateFlow("Length")
    val category: StateFlow<String> = _category.asStateFlow()

    private val _units = MutableStateFlow(UnitDefinitions.Length)
    val units: StateFlow<List<UnitDefinition>> = _units.asStateFlow()

    private val _fromUnit = MutableStateFlow(UnitDefinitions.Length[0])
    val fromUnit: StateFlow<UnitDefinition> = _fromUnit.asStateFlow()

    private val _toUnit = MutableStateFlow(UnitDefinitions.Length[1])
    val toUnit: StateFlow<UnitDefinition> = _toUnit.asStateFlow()

    private val _inputValue = MutableStateFlow("0")
    val inputValue: StateFlow<String> = _inputValue.asStateFlow()

    private val _outputValue = MutableStateFlow("")
    val outputValue: StateFlow<String> = _outputValue.asStateFlow()

    fun onEvent(event: ConverterEvent) {
        when (event) {
            is ConverterEvent.SetCategory -> {
                _category.value = event.category
                _units.value = UnitDefinitions.Categories[event.category] ?: UnitDefinitions.Length
                _fromUnit.value = _units.value[0]
                _toUnit.value = _units.value.getOrElse(1) { _units.value[0] }
                updateConversion()
            }
            is ConverterEvent.SetFromUnit -> {
                _fromUnit.value = event.unit
                updateConversion()
            }
            is ConverterEvent.SetToUnit -> {
                _toUnit.value = event.unit
                updateConversion()
            }
            is ConverterEvent.SwapUnits -> {
                val temp = _fromUnit.value
                _fromUnit.value = _toUnit.value
                _toUnit.value = temp
                updateConversion()
            }
            is ConverterEvent.OnInput -> {
                if (_inputValue.value == "0" && event.input != ".") {
                    _inputValue.value = event.input
                } else {
                    _inputValue.update { it + event.input }
                }
                updateConversion()
            }
            is ConverterEvent.OnDelete -> {
                if (_inputValue.value.length > 1) {
                    _inputValue.update { it.dropLast(1) }
                } else {
                    _inputValue.value = "0"
                }
                updateConversion()
            }
            is ConverterEvent.OnClear -> {
                _inputValue.value = "0"
                updateConversion()
            }
        }
    }

    private fun updateConversion() {
        _outputValue.value = convertUnitUseCase(_inputValue.value, _category.value, _fromUnit.value, _toUnit.value)
    }
}

sealed class ConverterEvent {
    data class SetCategory(val category: String) : ConverterEvent()
    data class SetFromUnit(val unit: UnitDefinition) : ConverterEvent()
    data class SetToUnit(val unit: UnitDefinition) : ConverterEvent()
    object SwapUnits : ConverterEvent()
    data class OnInput(val input: String) : ConverterEvent()
    object OnDelete : ConverterEvent()
    object OnClear : ConverterEvent()
}
