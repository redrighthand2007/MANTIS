package com.kush.mantis.features.programmer.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
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

    @Test
    fun `negative values show as 64-bit patterns outside decimal`() {
        assertEquals("-1", BaseConverter.convert(-1L, 10))
        assertEquals("FFFFFFFFFFFFFFFF", BaseConverter.convert(-1L, 16))
        assertEquals(-1L, BaseConverter.parse("FFFFFFFFFFFFFFFF", 16))
    }

    @Test
    fun `values beyond 64 bits are rejected instead of truncated`() {
        assertEquals(null, BaseConverter.parse("10000000000000000", 16))
        assertEquals(null, BaseConverter.parse("9223372036854775808", 10))
        assertEquals(Long.MAX_VALUE, BaseConverter.parse("9223372036854775807", 10))
    }

    @Test
    fun `digit validity depends on base`() {
        assertTrue(BaseConverter.isValidDigit('F', 16))
        assertFalse(BaseConverter.isValidDigit('8', 8))
        assertFalse(BaseConverter.isValidDigit('2', 2))
        assertTrue(BaseConverter.isValidDigit('9', 10))
    }
}
