package com.sample.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sample.data.room.dao.PlayerDao
import com.sample.data.room.dao.ShotDao
import com.sample.data.room.entities.PlayerEntity
import com.sample.data.room.entities.ShotEntity

@Database(
    entities = [PlayerEntity::class, ShotEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun playerDao(): PlayerDao
    abstract fun shotDao(): ShotDao
}