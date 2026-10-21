package com.kush.mantis.features.converter.presentation

import androidx.lifecycle.ViewModel
import com.kush.mantis.features.converter.domain.ConvertUnitUseCase
import com.kush.mantis.features.converter.domain.UnitDefinition
import com.kush.mantis.features.converter.domain.UnitDefinitions
import com.kush.mantis.features.programmer.domain.BaseConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    init {
        recalculate()
    }

    private val isBase get() = _category.value == "Base"

    fun onEvent(event: ConverterEvent) {
        when (event) {
            is ConverterEvent.SetCategory -> {
                val units = UnitDefinitions.Categories[event.category] ?: return
                val wasBase = isBase
                _category.value = event.category
                _units.value = units
                _fromUnit.value = units[0]
                _toUnit.value = units.getOrElse(1) { units[0] }
                // Base input (hex letters) and decimal input (".", "-") are not interchangeable
                if (wasBase != isBase) _inputValue.value = "0"
            }
            is ConverterEvent.SetFromUnit -> setFromUnit(event.unit)
            is ConverterEvent.SetToUnit -> _toUnit.value = event.unit
            is ConverterEvent.SwapUnits -> {
                val oldTo = _toUnit.value
                _toUnit.value = _fromUnit.value
                setFromUnit(oldTo)
            }
            is ConverterEvent.OnInput -> appendInput(event.input)
            is ConverterEvent.ToggleSign -> {
                if (isBase) return
                _inputValue.value = _inputValue.value.let { if (it.startsWith("-")) it.drop(1) else "-$it" }
            }
            is ConverterEvent.OnDelete -> {
                val trimmed = _inputValue.value.dropLast(1)
                _inputValue.value = if (trimmed.isEmpty() || trimmed == "-") "0" else trimmed
            }
            is ConverterEvent.OnClear -> _inputValue.value = "0"
            is ConverterEvent.OnEquals -> Unit
        }
        // Output always reflects the current input and units, no "=" needed
        recalculate()
    }

    private fun appendInput(input: String) {
        val current = _inputValue.value
        if (isBase) {
            val base = _fromUnit.value.toBase.toInt()
            if (input.length != 1 || !BaseConverter.isValidDigit(input[0], base)) return
            val candidate = (if (current == "0") "" else current) + input.uppercase()
            // Ignore digits that would overflow 64 bits
            if (BaseConverter.parse(candidate, base) == null) return
            _inputValue.value = candidate
            return
        }

        val negative = current.startsWith("-")
        val digits = current.removePrefix("-")
        val next = when {
            input == "." -> if ('.' in digits) return else "$digits."
            !input.all { it.isDigit() } -> return
            digits == "0" -> input
            digits.count { it.isDigit() } >= MAX_DIGITS -> return
            else -> digits + input
        }
        _inputValue.value = if (negative) "-$next" else next
    }

    private fun setFromUnit(unit: UnitDefinition) {
        if (isBase) {
            // Keep the same number when changing the input base, e.g. DEC 255 -> HEX FF
            val oldBase = _fromUnit.value.toBase.toInt()
            val value = BaseConverter.parse(_inputValue.value, oldBase) ?: 0L
            _inputValue.value = BaseConverter.convert(value, unit.toBase.toInt())
        }
        _fromUnit.value = unit
    }

    private fun recalculate() {
        _outputValue.value = convertUnitUseCase(_inputValue.value, _category.value, _fromUnit.value, _toUnit.value)
    }

    companion object {
        private const val MAX_DIGITS = 15
    }
}

sealed class ConverterEvent {
    data class SetCategory(val category: String) : ConverterEvent()
    data class SetFromUnit(val unit: UnitDefinition) : ConverterEvent()
    data class SetToUnit(val unit: UnitDefinition) : ConverterEvent()
    object SwapUnits : ConverterEvent()
    data class OnInput(val input: String) : ConverterEvent()
    object ToggleSign : ConverterEvent()
    object OnDelete : ConverterEvent()
    object OnClear : ConverterEvent()
    object OnEquals : ConverterEvent()
}
