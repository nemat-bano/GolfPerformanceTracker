package com.sample.data.di

import android.content.Context
import androidx.room.Room
import com.sample.data.room.AppDatabase
import com.sample.data.room.dao.PlayerDao
import com.sample.data.room.dao.ShotDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "golf_database"
        ).build()
    }

    @Provides
    fun providePlayerDao(db: AppDatabase): PlayerDao = db.playerDao()

    @Provides
    fun provideShotDao(db: AppDatabase): ShotDao = db.shotDao()
}