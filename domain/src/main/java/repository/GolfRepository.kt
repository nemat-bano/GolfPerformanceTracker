package repository

import com.sample.domain.model.Player

interface GolfRepository {
    suspend fun getPlayers(): List<Player>
}