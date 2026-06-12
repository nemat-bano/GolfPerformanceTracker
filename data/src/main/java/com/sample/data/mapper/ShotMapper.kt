package com.sample.data.mapper

import com.sample.data.dto.ShotDto
import com.sample.domain.model.Shot

fun ShotDto.toDomain(): Shot {
    return Shot(
        id = id,
        playerId = playerId,
        clubUsed = clubUsed,
        ballSpeed = ballSpeed,
        launchAngle = launchAngle,
        spinRate = spinRate,
        distance = distance
    )
}