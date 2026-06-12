package com.sample.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "players")
data class PlayerEntity(
    @PrimaryKey val id: String,
    val name: String,
    val club: String,
    val averageSpeed: Double,
    val averageDistance: Double,
    val imageUrl: String?
)
