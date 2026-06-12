package com.sample.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shots")
data class ShotEntity(
    @PrimaryKey val id: String,
    val playerId: String,
    val clubUsed: String,
    val ballSpeed: Double,
    val launchAngle: Double,
    val spinRate: Int,
    val distance: Double
)
