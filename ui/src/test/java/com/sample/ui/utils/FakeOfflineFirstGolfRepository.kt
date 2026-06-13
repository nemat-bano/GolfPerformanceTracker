package com.sample.ui.utils

import com.sample.domain.model.Player
import com.sample.domain.model.Shot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import repository.OfflineFirstGolfRepository

class FakeOfflineFirstGolfRepository : OfflineFirstGolfRepository {

    private val playersFlow = MutableStateFlow<List<Player>>(emptyList())
    private val shotsFlow = MutableStateFlow<List<Shot>>(emptyList())

    var syncPlayersCalled = false
        private set

    var syncPlayerDetailsCalled = false
        private set

    var syncedPlayerId: String? = null
        private set

    override fun observePlayers(): Flow<List<Player>> {
        return playersFlow
    }

    override fun observePlayer(id: String): Flow<Player?> {
        return playersFlow.map { players ->
            players.find { it.id == id }
        }
    }

    override fun observeShots(playerId: String): Flow<List<Shot>> {
        return shotsFlow.map { shots ->
            shots.filter { it.playerId == playerId }
        }
    }

    override suspend fun syncPlayers() {
        syncPlayersCalled = true
    }

    override suspend fun syncPlayerDetails(playerId: String) {
        syncPlayerDetailsCalled = true
        syncedPlayerId = playerId
    }

    fun emitPlayers(players: List<Player>) {
        playersFlow.value = players
    }

    fun emitShots(shots: List<Shot>) {
        shotsFlow.value = shots
    }
}