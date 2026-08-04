package com.kush.mantis.core.util

import android.view.HapticFeedbackConstants
import android.view.View

object HapticHelper {
    fun performHapticFeedback(view: View, enabled: Boolean) {
        if (enabled) {
            view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
        }
    }
}
