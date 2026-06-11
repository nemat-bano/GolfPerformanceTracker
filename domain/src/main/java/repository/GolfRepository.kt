package repository

import com.sample.domain.model.Player
import com.sample.domain.model.Shot

interface GolfRepository {
    suspend fun getPlayers(): List<Player>
    suspend fun getPlayer(
        playerId: String
    ): Player?

    suspend fun getShots(
        playerId: String
    ): List<Shot>

}