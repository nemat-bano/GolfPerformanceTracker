package com.sample.data.mapper

import com.sample.data.dto.PlayerDto
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