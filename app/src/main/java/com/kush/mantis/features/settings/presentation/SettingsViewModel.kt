package com.kush.mantis.features.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kush.mantis.features.settings.domain.SettingsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val useCases: SettingsUseCases
) : ViewModel() {

    val themeMode: StateFlow<String> = useCases.themeModeFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "System")

    val hapticFeedback: StateFlow<Boolean> = useCases.hapticFeedbackFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    fun setThemeMode(mode: String) {
        viewModelScope.launch { useCases.setThemeMode(mode) }
    }

    fun setHapticFeedback(enabled: Boolean) {
        viewModelScope.launch { useCases.setHapticFeedback(enabled) }
    }
}
