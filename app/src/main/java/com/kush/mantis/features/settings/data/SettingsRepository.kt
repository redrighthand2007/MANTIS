package com.kush.mantis.features.settings.data

import com.kush.mantis.core.data.SettingsDataStore
import javax.inject.Inject

class SettingsRepository @Inject constructor(
    private val dataStore: SettingsDataStore
) {
    val themeModeFlow = dataStore.themeModeFlow
    val decimalPlacesFlow = dataStore.decimalPlacesFlow
    val hapticFeedbackFlow = dataStore.hapticFeedbackFlow

    suspend fun setThemeMode(mode: String) = dataStore.setThemeMode(mode)
    suspend fun setDecimalPlaces(places: Int) = dataStore.setDecimalPlaces(places)
    suspend fun setHapticFeedback(enabled: Boolean) = dataStore.setHapticFeedback(enabled)
}
