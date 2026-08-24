package com.kush.mantis.features.history.domain

import com.kush.mantis.features.history.data.CalculationHistory
import com.kush.mantis.features.history.data.HistoryRepository
import javax.inject.Inject

class InsertHistoryUseCase @Inject constructor(
    private val repository: HistoryRepository
) {
    suspend operator fun invoke(history: CalculationHistory) {
        repository.insertHistory(history)
    }
}
