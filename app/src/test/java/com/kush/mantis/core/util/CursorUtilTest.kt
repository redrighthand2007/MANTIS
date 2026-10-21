package com.kush.mantis.core.util

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CursorUtilTest {

    @Test
    fun `insert replaces a backwards selection instead of duplicating text`() {
        val result = CursorUtil.insertText(TextFieldValue("12345", TextRange(4, 1)), "X")
        assertEquals("1X5", result.text)
        assertEquals(TextRange(2), result.selection)
    }

    @Test
    fun `delete removes a backwards selection`() {
        assertEquals("15", CursorUtil.deleteText(TextFieldValue("12345", TextRange(4, 1))).text)
    }

    @Test
    fun `decimal point allowed only once per number`() {
        assertFalse(CursorUtil.canInsertDecimal(TextFieldValue("1.2", TextRange(3))))
        assertFalse(CursorUtil.canInsertDecimal(TextFieldValue("1.23", TextRange(3))))
        assertFalse(CursorUtil.canInsertDecimal(TextFieldValue("12.3", TextRange(1))))
        assertTrue(CursorUtil.canInsertDecimal(TextFieldValue("1.2+3", TextRange(5))))
        assertTrue(CursorUtil.canInsertDecimal(TextFieldValue("", TextRange(0))))
    }
}
