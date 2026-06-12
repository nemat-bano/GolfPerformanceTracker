package com.sample.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sample.data.room.entities.PlayerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {

    @Query("SELECT * FROM players")
    suspend fun getPlayers(): List<PlayerEntity>

    @Query("SELECT * FROM players WHERE id = :id")
    suspend fun getPlayer(id: String): PlayerEntity?

    @Query("SELECT * FROM players ORDER BY name")
    fun observePlayers(): Flow<List<PlayerEntity>>

    @Query("SELECT * FROM players WHERE id = :id")
    fun observePlayer(id: String): Flow<PlayerEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updatePlayers(players: List<PlayerEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updatePlayer(player: PlayerEntity)
}