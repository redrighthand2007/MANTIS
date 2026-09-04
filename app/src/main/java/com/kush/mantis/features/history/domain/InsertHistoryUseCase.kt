package com.kush.mantis.features.history.domain

import com.kush.mantis.features.history.data.CalculationHistory
import com.kush.mantis.features.history.data.HistoryRepository
import javax.inject.Inject

class InsertHistoryUseCase @Inject constructor(
    private val repository: HistoryRepository
) {
    suspend operator fun invoke(history: CalculationHistory) {
        repository.insertHistory(history)
        
        // Clean up history older than 2 weeks (14 days)
        val twoWeeksInMillis = 14L * 24 * 60 * 60 * 1000
        val cutoff = System.currentTimeMillis() - twoWeeksInMillis
        repository.deleteHistoryOlderThan(cutoff)
    }
}
