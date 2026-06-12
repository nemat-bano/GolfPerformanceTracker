package repository

import com.sample.domain.model.Player
import com.sample.domain.model.Shot
import kotlinx.coroutines.flow.Flow

interface OfflineFirstGolfRepository {
    fun observePlayers(): Flow<List<Player>>

    fun observePlayer(id: String): Flow<Player?>

    fun observeShots(playerId: String): Flow<List<Shot>>

    suspend fun syncPlayers()

    suspend fun syncPlayerDetails(playerId: String)
}