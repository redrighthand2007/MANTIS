package com.kush.mantis.core.util

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

object CursorUtil {
    fun insertText(current: TextFieldValue, input: String): TextFieldValue {
        val text = current.text
        val selection = current.selection
        val newText = text.substring(0, selection.start) + input + text.substring(selection.end)
        val newCursor = selection.start + input.length
        return TextFieldValue(newText, TextRange(newCursor))
    }

    fun deleteText(current: TextFieldValue): TextFieldValue {
        val text = current.text
        val selection = current.selection
        if (selection.start == selection.end && selection.start > 0) {
            val newText = text.substring(0, selection.start - 1) + text.substring(selection.end)
            return TextFieldValue(newText, TextRange(selection.start - 1))
        } else if (selection.start != selection.end) {
            val newText = text.substring(0, selection.start) + text.substring(selection.end)
            return TextFieldValue(newText, TextRange(selection.start))
        }
        return current
    }

    fun clear(): TextFieldValue {
        return TextFieldValue("")
    }
}
