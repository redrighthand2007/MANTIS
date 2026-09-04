package com.kush.mantis.features.history.domain

import com.kush.mantis.features.history.data.CalculationHistory
import com.kush.mantis.features.history.data.HistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

import kotlinx.coroutines.flow.onStart

class GetHistoryUseCase @Inject constructor(
    private val repository: HistoryRepository
) {
    operator fun invoke(): Flow<List<CalculationHistory>> {
        return repository.getAllHistory().onStart {
            val twoWeeksInMillis = 14L * 24 * 60 * 60 * 1000
            val cutoff = System.currentTimeMillis() - twoWeeksInMillis
            repository.deleteHistoryOlderThan(cutoff)
        }
    }
}
