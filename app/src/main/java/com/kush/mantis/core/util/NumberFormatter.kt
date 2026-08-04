package com.kush.mantis.core.util

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

object NumberFormatter {
    fun formatResult(value: Double, maxDecimals: Int = 8): String {
        val df = DecimalFormat()
        df.maximumFractionDigits = maxDecimals
        df.isGroupingUsed = true // Add commas
        val symbols = DecimalFormatSymbols(Locale.US)
        symbols.groupingSeparator = ','
        symbols.decimalSeparator = '.'
        df.decimalFormatSymbols = symbols
        
        var formatted = df.format(value)
        // Handle -0
        if (formatted == "-0") {
            formatted = "0"
        }
        return formatted
    }
}
