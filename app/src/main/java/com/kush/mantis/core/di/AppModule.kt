package com.kush.mantis.core.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.kush.mantis.core.data.MantisDatabase
import com.kush.mantis.core.data.SettingsDataStore
import com.kush.mantis.features.history.data.HistoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMantisDatabase(app: Application): MantisDatabase {
        return Room.databaseBuilder(
            app,
            MantisDatabase::class.java,
            "mantis_db"
        )
        .fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    @Singleton
    fun provideHistoryDao(db: MantisDatabase): HistoryDao {
        return db.historyDao()
    }

    @Provides
    @Singleton
    fun provideSettingsDataStore(@ApplicationContext context: Context): SettingsDataStore {
        return SettingsDataStore(context)
    }
}
