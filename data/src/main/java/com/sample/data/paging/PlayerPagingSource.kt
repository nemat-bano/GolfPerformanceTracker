package com.sample.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sample.data.mapper.toDomain
import com.sample.data.service.GolfService
import com.sample.domain.model.Player

class PlayerPagingSource(
    private val api: GolfService
) : PagingSource<Int, Player>() {

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, Player> {
        val page = params.key ?: 1
        val limit = 5

        return try {
            val players = api.getPlayers(
                page = page,
                limit = limit
            ).map { it.toDomain() }

            LoadResult.Page(
                data = players,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (players.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(
        state: PagingState<Int, Player>
    ): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}