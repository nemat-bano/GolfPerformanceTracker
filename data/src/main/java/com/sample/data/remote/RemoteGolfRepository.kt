package com.sample.data.remote

import com.sample.data.service.GolfService
import com.sample.data.dto.PlayerDto
import com.sample.domain.model.Player
import com.sample.domain.model.Shot
import repository.GolfRepository
import javax.inject.Inject
import com.sample.data.mapper.toDomain


class RemoteGolfRepository @Inject constructor(
    private val service: GolfService
) : GolfRepository {

    override suspend fun getPlayers(): List<Player> {
        return service.getPlayers().map(PlayerDto::toDomain)
    }

    override suspend fun getPlayer(
        playerId: String
    ): Player? {
        return service.getPlayer(playerId).toDomain()
    }

    override suspend fun getShots(
        playerId: String
    ): List<Shot> {
        return service.getShots(playerId).map { it.toDomain() }
    }
}