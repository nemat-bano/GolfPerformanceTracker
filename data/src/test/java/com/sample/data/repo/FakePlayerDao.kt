package com.sample.data.repo

import com.sample.data.room.dao.PlayerDao
import com.sample.data.room.entities.PlayerEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakePlayerDao : PlayerDao {

    private val playersFlow = MutableStateFlow<List<PlayerEntity>>(emptyList())
    override suspend fun getPlayers(): List<PlayerEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getPlayer(id: String): PlayerEntity? {
        TODO("Not yet implemented")
    }

    override fun observePlayers(): Flow<List<PlayerEntity>> = playersFlow

    override fun observePlayer(id: String): Flow<PlayerEntity?> {
        return playersFlow.map { players ->
            players.find { it.id == id }
        }
    }

    override suspend fun upsertPlayer(players: List<PlayerEntity>) {
        TODO("Not yet implemented")
    }

    override suspend fun clearPlayers() {
        TODO("Not yet implemented")
    }

    override suspend fun updatePlayer(player: PlayerEntity) {
        TODO("Not yet implemented")
    }

    fun emitPlayers(players: List<PlayerEntity>) {
        playersFlow.value = players
    }
}