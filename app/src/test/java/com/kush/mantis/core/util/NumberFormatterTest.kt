package com.kush.mantis.core.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class NumberFormatterTest {

    @Test
    fun `formats with grouping and trims floating point noise`() {
        assertEquals("1,234", NumberFormatter.formatResult(1234.0))
        assertEquals("0.3", NumberFormatter.formatResult(0.1 + 0.2))
        assertEquals("0.3333333333", NumberFormatter.formatResult(1.0 / 3.0))
        assertEquals("999,999,999,999,999", NumberFormatter.formatResult(999_999_999_999_999.0))
        assertEquals("0", NumberFormatter.formatResult(-0.0))
    }

    @Test
    fun `tiny and huge values use scientific notation instead of rounding to zero or fake digits`() {
        assertEquals("3.33333333333333E-10", NumberFormatter.formatResult(1.0 / 3_000_000_000.0))
        assertEquals("-1E-12", NumberFormatter.formatResult(-1e-12))
        assertEquals("1.84467440737096E19", NumberFormatter.formatResult(1.8446744073709552E19))
    }

    @Test
    fun `NaN and infinity have no display value`() {
        assertNull(NumberFormatter.formatResult(Double.NaN))
        assertNull(NumberFormatter.formatResult(Double.POSITIVE_INFINITY))
        assertNull(NumberFormatter.toExpression(Double.NEGATIVE_INFINITY))
    }

    @Test
    fun `expression form keeps full precision and has no commas`() {
        assertEquals("1234.5", NumberFormatter.toExpression(1234.5))
        assertEquals("3.14159265358979", NumberFormatter.toExpression(Math.PI))
        assertEquals("1E20", NumberFormatter.toExpression(1e20))
        assertEquals("-2.5E-7", NumberFormatter.toExpression(-2.5e-7))
    }
}
