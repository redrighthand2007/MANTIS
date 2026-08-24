package com.kush.mantis.features.history.domain

import com.kush.mantis.features.history.data.CalculationHistory
import com.kush.mantis.features.history.data.HistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHistoryUseCase @Inject constructor(
    private val repository: HistoryRepository
) {
    operator fun invoke(): Flow<List<CalculationHistory>> {
        return repository.getAllHistory()
    }
}
