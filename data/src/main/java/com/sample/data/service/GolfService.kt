package com.sample.data.service

import com.sample.data.dto.PlayerDto
import com.sample.data.dto.ShotDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GolfService {
    @GET("Players")
    suspend fun getPlayers(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): List<PlayerDto>

    @GET("Players")
    suspend fun getPlayers(): List<PlayerDto>

    @GET("Players/{id}")
    suspend fun getPlayer(
        @Path("id") playerId: String
    ): PlayerDto

    @GET("shots")
    suspend fun getShots(
        @Query("playerId") playerId: String
    ): List<ShotDto>
}