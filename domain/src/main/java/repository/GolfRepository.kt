package repository

import androidx.paging.PagingData
import com.sample.domain.model.Player
import com.sample.domain.model.Shot
import kotlinx.coroutines.flow.Flow

interface GolfRepository {
    fun getPagedPlayers(): Flow<PagingData<Player>>
    suspend fun getPlayers(): List<Player>
    suspend fun getPlayer(
        playerId: String
    ): Player?

    suspend fun getShots(
        playerId: String
    ): List<Shot>

}