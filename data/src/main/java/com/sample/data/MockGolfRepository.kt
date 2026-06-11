package com.sample.data

import com.sample.domain.model.Player
import com.sample.domain.model.Shot
import repository.GolfRepository
import javax.inject.Inject

class MockGolfRepository @Inject constructor() : GolfRepository {
    private val players = listOf(
        Player(
            id = "1",
            name = "Rory McIlroy",
            club = "Driver",
            averageSpeed = 178.5,
            averageDistance = 325.0,
            imageUrl = null
        ),
        Player(
            id = "2",
            name = "Tiger Woods",
            club = "Iron",
            averageSpeed = 164.2,
            averageDistance = 295.0,
            imageUrl = null
        ),
        Player(
            id = "3",
            name = "Nelly Korda",
            club = "Hybrid",
            averageSpeed = 151.8,
            averageDistance = 275.0,
            imageUrl = null
        ),
        Player(
            id = "4",
            name = "Scottie Scheffler",
            club = "Driver",
            averageSpeed = 182.0,
            averageDistance = 332.0,
            imageUrl = null
        )
    )

    private val shots = listOf(
        Shot(
            id = "1",
            playerId = "1",
            ballSpeed = 180.2,
            launchAngle = 12.5,
            clubUsed = "Driver",
            spinRate = 2600,
            distance = 330.0
        ),
        Shot(
            id = "2",
            playerId = "3",
            ballSpeed = 176.8,
            launchAngle = 11.8,
            clubUsed = "Driver",
            spinRate = 2550,
            distance = 322.0
        ),
        Shot(
            id = "3",
            playerId = "2",
            ballSpeed = 165.0,
            launchAngle = 14.2,
            clubUsed = "Iron",
            spinRate = 6200,
            distance = 285.0
        )
    )

    override suspend fun getPlayers(): List<Player> {
        return players
    }

    override suspend fun getPlayer(
        playerId: String
    ): Player? {
        return players.firstOrNull {
            it.id == playerId
        }
    }

    override suspend fun getShots(
        playerId: String
    ): List<Shot> {
        return shots.filter {
            it.playerId == playerId
        }
    }

}