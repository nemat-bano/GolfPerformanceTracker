package com.sample.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sample.data.room.entities.ShotEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShotDao {

    @Query("SELECT * FROM shots WHERE playerId = :playerId")
    suspend fun getShots(playerId: String): List<ShotEntity>

    @Query("SELECT * FROM shots WHERE playerId = :playerId")
    fun observeShots(playerId: String): Flow<List<ShotEntity>>

    @Query("DELETE FROM shots WHERE playerId = :playerId")
    suspend fun deleteShotsForPlayer(playerId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateShots(shots: List<ShotEntity>)
}