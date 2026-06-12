package com.sample.data.mapper

import com.sample.data.dto.ShotDto
import com.sample.data.room.entities.ShotEntity
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

fun ShotDto.toEntity(playerId: String) = ShotEntity(
    id = id,
    playerId = playerId,
    clubUsed = clubUsed,
    ballSpeed = ballSpeed,
    launchAngle = launchAngle,
    spinRate = spinRate,
    distance = distance
)

fun ShotEntity.toDomain() = Shot(
    id = id,
    playerId = playerId,
    clubUsed = clubUsed,
    ballSpeed = ballSpeed,
    launchAngle = launchAngle,
    spinRate = spinRate,
    distance = distance
)