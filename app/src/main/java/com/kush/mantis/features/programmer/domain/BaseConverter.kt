package com.kush.mantis.features.programmer.domain

object BaseConverter {
    val SUPPORTED_BASES = setOf(2, 8, 10, 16)

    /** DEC is signed; HEX/OCT/BIN show the raw 64-bit two's-complement pattern (NOT 0 -> FFFF...). */
    fun convert(value: Long, targetBase: Int): String {
        return when (targetBase) {
            10 -> value.toString()
            in SUPPORTED_BASES -> value.toULong().toString(targetBase).uppercase()
            else -> ""
        }
    }

    /** Parses a value typed in [base]; null if it has invalid digits or does not fit in 64 bits. */
    fun parse(value: String, base: Int): Long? {
        if (base !in SUPPORTED_BASES) return null
        if (value.isBlank()) return 0L
        return if (base == 10) {
            value.toLongOrNull()
        } else {
            value.toULongOrNull(base)?.toLong()
        }
    }

    fun isValidDigit(digit: Char, base: Int): Boolean = digit.digitToIntOrNull(base) != null
}
