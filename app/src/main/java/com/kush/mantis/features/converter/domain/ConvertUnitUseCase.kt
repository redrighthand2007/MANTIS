package com.kush.mantis.features.converter.domain

import com.kush.mantis.core.util.NumberFormatter
import javax.inject.Inject

class ConvertUnitUseCase @Inject constructor() {
    operator fun invoke(valueStr: String, category: String, fromUnit: UnitDefinition, toUnit: UnitDefinition): String {
        if (valueStr.isEmpty()) return ""

        if (category == "Base") {
            return try {
                val baseFrom = fromUnit.toBase.toInt()
                val baseTo = toUnit.toBase.toInt()
                val decimalValue = valueStr.toLong(baseFrom)
                decimalValue.toString(baseTo).uppercase()
            } catch (e: Exception) {
                "Error"
            }
        }

        val value = valueStr.toDoubleOrNull() ?: return ""
        
        if (category == "Temperature") {
            val celsius = when (fromUnit.symbol) {
                "°F" -> (value - 32) * 5 / 9
                "K" -> value - 273.15
                else -> value
            }
            val result = when (toUnit.symbol) {
                "°F" -> (celsius * 9 / 5) + 32
                "K" -> celsius + 273.15
                else -> celsius
            }
            return NumberFormatter.formatResult(result, 4)
        }

        val baseValue = value * fromUnit.toBase
        val result = baseValue / toUnit.toBase
        return NumberFormatter.formatResult(result, 6)
    }
}
