package com.sample.data

import com.sample.domain.model.Player
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

    override suspend fun getPlayers(): List<Player> {
        return players
    }

}