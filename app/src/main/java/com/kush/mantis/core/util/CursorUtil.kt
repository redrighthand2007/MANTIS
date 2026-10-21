package com.kush.mantis.core.util

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

object CursorUtil {
    // Selections can be reversed (start > end) when dragged backwards, so always use min/max
    fun insertText(current: TextFieldValue, input: String): TextFieldValue {
        val text = current.text
        val start = current.selection.min
        val end = current.selection.max
        val newText = text.substring(0, start) + input + text.substring(end)
        val newCursor = start + input.length
        return TextFieldValue(newText, TextRange(newCursor))
    }

    fun deleteText(current: TextFieldValue): TextFieldValue {
        val text = current.text
        val start = current.selection.min
        val end = current.selection.max
        if (start == end && start > 0) {
            val newText = text.substring(0, start - 1) + text.substring(end)
            return TextFieldValue(newText, TextRange(start - 1))
        } else if (start != end) {
            val newText = text.substring(0, start) + text.substring(end)
            return TextFieldValue(newText, TextRange(start))
        }
        return current
    }

    fun clear(): TextFieldValue {
        return TextFieldValue("")
    }

    /** False if the number around the cursor already has a decimal point (e.g. "1.2|3"). */
    fun canInsertDecimal(current: TextFieldValue): Boolean {
        val text = current.text
        val isNumberChar = { c: Char -> c.isDigit() || c == '.' }
        var left = current.selection.min
        while (left > 0 && isNumberChar(text[left - 1])) left--
        var right = current.selection.max
        while (right < text.length && isNumberChar(text[right])) right++
        val numberAroundCursor = text.substring(left, current.selection.min) + text.substring(current.selection.max, right)
        return '.' !in numberAroundCursor
    }
}
