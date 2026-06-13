package com.sample.data.mapper

import com.sample.data.dto.PlayerDto
import com.sample.data.room.entities.PlayerEntity
import com.sample.domain.model.Player


fun PlayerDto.toDomain(): Player {
    return Player(
        id = id,
        name = name,
        club = club,
        averageSpeed = averageSpeed,
        averageDistance = averageDistance,
        imageUrl = avatar
    )
}

fun PlayerDto.toEntity() = PlayerEntity(
    id = id,
    name = name,
    club = club,
    averageSpeed = averageSpeed,
    averageDistance = averageDistance,
    imageUrl = avatar
)

fun PlayerEntity.toDomain() = Player(
    id = id,
    name = name,
    club = club,
    averageSpeed = averageSpeed,
    averageDistance = averageDistance,
    imageUrl = imageUrl
)
