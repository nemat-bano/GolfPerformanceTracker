package com.sample.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sample.data.service.GolfService
import com.sample.data.dto.PlayerDto
import com.sample.domain.model.Player
import com.sample.domain.model.Shot
import repository.GolfRepository
import javax.inject.Inject
import com.sample.data.mapper.toDomain
import com.sample.data.paging.PlayerPagingSource
import kotlinx.coroutines.flow.Flow

class RemoteGolfRepository @Inject constructor(
    private val service: GolfService
) : GolfRepository {
    override fun getPagedPlayers(): Flow<PagingData<Player>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                initialLoadSize = 5,
                prefetchDistance = 1,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                PlayerPagingSource(service)
            }
        ).flow
    }

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