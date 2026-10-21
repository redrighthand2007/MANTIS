package com.kush.mantis.core.util

import java.math.BigDecimal
import java.math.MathContext
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

object NumberFormatter {
    // A Double carries ~15-17 significant digits; anything beyond 15 is floating-point noise
    private val SIGNIFICANT = MathContext(15)
    private val SCIENTIFIC_UPPER = BigDecimal("1e15")
    private val SCIENTIFIC_LOWER = BigDecimal("1e-6")

    /** Human-readable result with grouping commas, or null if the value is NaN/infinite. */
    fun formatResult(value: Double, maxDecimals: Int = 10): String? {
        val rounded = round(value) ?: return null
        // Show every significant digit: fewer would round 9.99999999998E23 up to a misleading 1E24
        if (needsScientific(rounded)) return scientific(rounded, maxSignificant = 15)

        val df = DecimalFormat("#,##0", DecimalFormatSymbols(Locale.US))
        df.maximumFractionDigits = maxDecimals
        val formatted = df.format(rounded)
        return if (formatted == "-0") "0" else formatted
    }

    /** Full-precision form without grouping commas, safe to feed back into the expression parser. */
    fun toExpression(value: Double): String? {
        val rounded = round(value) ?: return null
        if (needsScientific(rounded)) return scientific(rounded, maxSignificant = 15)
        val plain = rounded.toPlainString()
        return if (plain == "-0") "0" else plain
    }

    private fun round(value: Double): BigDecimal? {
        if (value.isNaN() || value.isInfinite()) return null
        if (value == 0.0) return BigDecimal.ZERO
        return BigDecimal(value).round(SIGNIFICANT).stripTrailingZeros()
    }

    private fun needsScientific(value: BigDecimal): Boolean {
        val abs = value.abs()
        return abs.signum() != 0 && (abs >= SCIENTIFIC_UPPER || abs < SCIENTIFIC_LOWER)
    }

    private fun scientific(value: BigDecimal, maxSignificant: Int): String {
        val mantissaDigits = value.round(MathContext(maxSignificant)).stripTrailingZeros()
        val exponent = mantissaDigits.precision() - mantissaDigits.scale() - 1
        val mantissa = mantissaDigits.movePointLeft(exponent).stripTrailingZeros().toPlainString()
        return "${mantissa}E$exponent"
    }
}
