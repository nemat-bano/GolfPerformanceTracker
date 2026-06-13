package com.sample.data.mapper

import com.sample.data.dto.ShotDto
import com.sample.data.room.entities.ShotEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class ShotMapperTest {

    @Test
    fun `toDomain maps ShotDto to Shot`() {

        val dto = ShotDto(
            id = "shot1",
            playerId = "1",
            clubUsed = "club",
            ballSpeed = 182.5,
            launchAngle = 13.2,
            spinRate = 2400,
            distance = 325.0
        )

        val result = dto.toDomain()

        assertEquals("shot1", result.id)
        assertEquals("1", result.playerId)
        assertEquals("club", result.clubUsed)
        assertEquals(182.5, result.ballSpeed, 0.0)
        assertEquals(13.2, result.launchAngle, 0.0)
        assertEquals(2400, result.spinRate)
        assertEquals(325.0, result.distance, 0.0)
    }

    @Test
    fun `toEntity maps ShotDto to ShotEntity`() {

        val dto = ShotDto(
            id = "shot1",
            playerId = "1",
            clubUsed = "club",
            ballSpeed = 182.5,
            launchAngle = 13.2,
            spinRate = 2400,
            distance = 325.0
        )

        val result = dto.toEntity("1")

        assertEquals("shot1", result.id)
        assertEquals("1", result.playerId)
        assertEquals("club", result.clubUsed)
        assertEquals(182.5, result.ballSpeed, 0.0)
        assertEquals(13.2, result.launchAngle, 0.0)
        assertEquals(2400, result.spinRate)
        assertEquals(325.0, result.distance, 0.0)
    }

    @Test
    fun `toDomain maps ShotEntity to Shot`() {

        val entity = ShotEntity(
            id = "shot1",
            playerId = "1",
            clubUsed = "club",
            ballSpeed = 182.5,
            launchAngle = 13.2,
            spinRate = 2400,
            distance = 325.0
        )

        val result = entity.toDomain()

        assertEquals("shot1", result.id)
        assertEquals("1", result.playerId)
        assertEquals("club", result.clubUsed)
        assertEquals(182.5, result.ballSpeed, 0.0)
        assertEquals(13.2, result.launchAngle, 0.0)
        assertEquals(2400, result.spinRate)
        assertEquals(325.0, result.distance, 0.0)
    }
}