package com.kush.mantis.core.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.InterceptPlatformTextInput
import kotlinx.coroutines.awaitCancellation
import androidx.compose.ui.ExperimentalComposeUiApi

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
