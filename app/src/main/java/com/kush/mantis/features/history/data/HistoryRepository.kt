package com.kush.mantis.features.history.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HistoryRepository @Inject constructor(
    private val dao: HistoryDao
) {
    fun getAllHistory(): Flow<List<CalculationHistory>> = dao.getAllHistory()

    suspend fun insertHistory(history: CalculationHistory) {
        dao.insertHistory(history)
    }

    suspend fun clearHistory() {
        dao.clearHistory()
    }
}
