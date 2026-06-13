package com.sample.data.repo

import com.sample.data.room.dao.ShotDao
import com.sample.data.room.entities.ShotEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeShotDao : ShotDao {

    private val shotsFlow = MutableStateFlow<List<ShotEntity>>(emptyList())
    override suspend fun getShots(playerId: String): List<ShotEntity> {
        TODO("Not yet implemented")
    }

    override fun observeShots(playerId: String): Flow<List<ShotEntity>> {
        return shotsFlow.map { shots ->
            shots.filter { it.playerId == playerId }
        }
    }

    override suspend fun deleteShotsForPlayer(playerId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateShots(shots: List<ShotEntity>) {
        TODO("Not yet implemented")
    }

    fun emitShots(shots: List<ShotEntity>) {
        shotsFlow.value = shots
    }
}