package com.sample.data.repo

import android.util.Log
import com.sample.data.mapper.toDomain
import com.sample.data.mapper.toEntity
import com.sample.data.room.dao.PlayerDao
import com.sample.data.room.dao.ShotDao
import com.sample.data.service.GolfService
import com.sample.domain.model.Player
import com.sample.domain.model.Shot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import repository.OfflineFirstGolfRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfflineFirstGolfRepositoryImpl @Inject constructor(
    private val api: GolfService,
    private val playerDao: PlayerDao,
    private val shotDao: ShotDao
) : OfflineFirstGolfRepository {

    override fun observePlayers(): Flow<List<Player>> {
        return playerDao.observePlayers()
            .map { players ->
                players.map { it.toDomain() }
            }
    }

    override fun observePlayer(id: String): Flow<Player?> {
        return playerDao.observePlayer(id)
            .map { it?.toDomain() }
    }

    override fun observeShots(playerId: String): Flow<List<Shot>> {
        return shotDao.observeShots(playerId)
            .map { shots ->
                shots.map { it.toDomain() }
            }
    }

    override suspend fun syncPlayers() {
        val remotePlayers = api.getPlayers()

        playerDao.updatePlayers(
            remotePlayers.map { it.toEntity() }
        )
    }

    override suspend fun syncPlayerDetails(playerId: String) {

        try {
            val player = api.getPlayer(playerId)

            playerDao.updatePlayer(
                player.toEntity()
            )
        } catch (e: Exception) {
            Log.e(
                "PlayerListViewModel",
            "Player detail sync failed. Room will continue showing cached player data.")
        }

        try {
            val shots = api.getShots(playerId)

            shotDao.deleteShotsForPlayer(playerId)

            shotDao.updateShots(
                shots.map {
                    it.toEntity(playerId)
                }
            )
        } catch (e: Exception) {
            Log.e(
                "PlayerListViewModel",
                "Shots sync failed. Room will continue showing cached shots or empty list.")
        }
    }
}