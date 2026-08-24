package com.kush.mantis.features.settings.domain

import com.kush.mantis.features.settings.data.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsUseCases @Inject constructor(
    private val repository: SettingsRepository
) {
    val themeModeFlow: Flow<String> = repository.themeModeFlow
    val hapticFeedbackFlow: Flow<Boolean> = repository.hapticFeedbackFlow

    suspend fun setThemeMode(mode: String) {
        repository.setThemeMode(mode)
    }

    suspend fun setHapticFeedback(enabled: Boolean) {
        repository.setHapticFeedback(enabled)
    }
}
