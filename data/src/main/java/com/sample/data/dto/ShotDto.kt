package com.sample.data.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ShotDto(
    val id: String,
    val playerId: String,
    val clubUsed: String,
    val ballSpeed: Double,
    val launchAngle: Double,
    val spinRate: Int,
    val distance: Double
)
