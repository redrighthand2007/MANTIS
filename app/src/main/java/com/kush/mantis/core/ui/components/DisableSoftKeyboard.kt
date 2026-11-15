package com.kush.mantis.core.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.InterceptPlatformTextInput
import kotlinx.coroutines.awaitCancellation

/** Keeps the cursor and selection in a text field but never shows the system keyboard; the keypad is the input. */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DisableSoftKeyboard(content: @Composable () -> Unit) {
    InterceptPlatformTextInput(
        interceptor = { _, _ ->
            awaitCancellation()
        },
        content = content
    )
}
