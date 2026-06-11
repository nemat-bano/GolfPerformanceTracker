package com.sample.domain.model

data class Player(
    val id: String,
    val name: String,
    val club: String,
    val averageSpeed: Double,
    val averageDistance: Double,
    val imageUrl: String?
)