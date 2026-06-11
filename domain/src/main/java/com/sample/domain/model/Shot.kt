package com.sample.domain.model

data class Shot(
    val id: String,
    val playerId: String,
    val ballSpeed: Double,
    val launchAngle: Double,
    val clubUsed: String,
    val spinRate: Int,
    val distance: Double
)