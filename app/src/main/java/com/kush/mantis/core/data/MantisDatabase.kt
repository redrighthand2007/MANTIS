package com.kush.mantis.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kush.mantis.features.history.data.CalculationHistory
import com.kush.mantis.features.history.data.HistoryDao

@Database(entities = [CalculationHistory::class], version = 3, exportSchema = false)
abstract class MantisDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}
