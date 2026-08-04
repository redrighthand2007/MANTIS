package com.kush.mantis.features.programmer.domain

object BaseConverter {
    fun convert(value: Long, targetBase: Int): String {
        return when (targetBase) {
            10 -> value.toString(10)
            16 -> value.toString(16).uppercase()
            8 -> value.toString(8)
            2 -> value.toString(2)
            else -> ""
        }
    }
    
    fun parse(value: String, base: Int): Long? {
        return try {
            if (value.isBlank()) 0L else value.toLong(base)
        } catch (e: Exception) {
            null
        }
    }
}
