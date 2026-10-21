package com.kush.mantis.features.converter.domain

import com.kush.mantis.core.util.NumberFormatter
import com.kush.mantis.features.programmer.domain.BaseConverter
import javax.inject.Inject

class ConvertUnitUseCase @Inject constructor() {
    operator fun invoke(valueStr: String, category: String, fromUnit: UnitDefinition, toUnit: UnitDefinition): String {
        if (valueStr.isEmpty() || valueStr == "-") return ""

        if (category == "Base") {
            val decimalValue = BaseConverter.parse(valueStr, fromUnit.toBase.toInt()) ?: return ERROR
            return BaseConverter.convert(decimalValue, toUnit.toBase.toInt())
        }

        val value = valueStr.toDoubleOrNull() ?: return ERROR

        if (category == "Temp") {
            val celsius = when (fromUnit.symbol) {
                "°F" -> (value - 32) * 5 / 9
                "K" -> value - 273.15
                else -> value
            }
            // Nothing can be colder than absolute zero (small tolerance for -459.67 °F rounding)
            if (celsius < ABSOLUTE_ZERO_C - 1e-9) return ERROR
            val result = when (toUnit.symbol) {
                "°F" -> (celsius * 9 / 5) + 32
                "K" -> celsius + 273.15
                else -> celsius
            }
            return NumberFormatter.formatResult(result) ?: ERROR
        }

        val baseValue = value * fromUnit.toBase
        val result = baseValue / toUnit.toBase
        return NumberFormatter.formatResult(result) ?: ERROR
    }

    companion object {
        const val ERROR = "Error"
        private const val ABSOLUTE_ZERO_C = -273.15
    }
}
