package com.kush.mantis.features.programmer.domain

import org.junit.Assert.assertEquals
import org.junit.Test

class BaseConverterTest {

    @Test
    fun `convert decimal to other bases`() {
        assertEquals("15", BaseConverter.convert(15L, 10))
        assertEquals("F", BaseConverter.convert(15L, 16))
        assertEquals("17", BaseConverter.convert(15L, 8))
        assertEquals("1111", BaseConverter.convert(15L, 2))
    }

    @Test
    fun `convert hexadecimal to other bases`() {
        // Hexadecimal "1A" is decimal 26
        val decValue = BaseConverter.parse("1A", 16)
        assertEquals(26L, decValue)
        
        if (decValue != null) {
            assertEquals("26", BaseConverter.convert(decValue, 10))
            assertEquals("32", BaseConverter.convert(decValue, 8))
            assertEquals("11010", BaseConverter.convert(decValue, 2))
        }
    }

    @Test
    fun `parse handles empty or invalid string gracefully`() {
        assertEquals(0L, BaseConverter.parse("", 10))
        assertEquals(0L, BaseConverter.parse("   ", 16))
        assertEquals(null, BaseConverter.parse("XYZ", 10))
        assertEquals(null, BaseConverter.parse("2", 2)) // '2' is invalid in binary
    }
}
