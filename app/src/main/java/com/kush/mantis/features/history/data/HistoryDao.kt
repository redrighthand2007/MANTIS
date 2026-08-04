package com.kush.mantis.features.history.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Query("SELECT * FROM history_table ORDER BY timestamp DESC")
    fun getAllHistory(): Flow<List<CalculationHistory>>

    @Insert
    suspend fun insertHistory(history: CalculationHistory)

    @Query("DELETE FROM history_table")
    suspend fun clearHistory()
}
