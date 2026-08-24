package com.kush.mantis.features.history.domain

import com.kush.mantis.features.history.data.HistoryRepository
import javax.inject.Inject

class ClearHistoryUseCase @Inject constructor(
    private val repository: HistoryRepository
) {
    suspend operator fun invoke() {
        repository.clearHistory()
    }
}
