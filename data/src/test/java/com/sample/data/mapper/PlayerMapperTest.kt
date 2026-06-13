package com.sample.data.mapper

import com.sample.data.dto.PlayerDto
import com.sample.data.room.entities.PlayerEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class PlayerMapperTest {

    @Test
    fun `toDomain maps PlayerDto to Player`() {
        val dto = PlayerDto(
            id = "1",
            name = "Player Name",
            club = "Club 1",
            averageSpeed = 165.0,
            averageDistance = 290.0,
            avatar = "https://example.com/1.jpg"
        )

        val result = dto.toDomain()

        assertEquals("1", result.id)
        assertEquals("Player Name", result.name)
        assertEquals("Club 1", result.club)
        assertEquals(165.0, result.averageSpeed, 0.0)
        assertEquals(290.0, result.averageDistance, 0.0)
        assertEquals("https://example.com/1.jpg", result.imageUrl)
    }

    @Test
    fun `toEntity maps PlayerDto to PlayerEntity`() {
        val dto = PlayerDto(
            id = "1",
            name = "Player Name",
            club = "Club 1",
            averageSpeed = 165.0,
            averageDistance = 290.0,
            avatar = "https://example.com/1.jpg"
        )

        val result = dto.toEntity()

        assertEquals("1", result.id)
        assertEquals("Player Name", result.name)
        assertEquals("Club 1", result.club)
        assertEquals(165.0, result.averageSpeed, 0.0)
        assertEquals(290.0, result.averageDistance, 0.0)
        assertEquals("https://example.com/1.jpg", result.imageUrl)
    }

    @Test
    fun `toDomain maps PlayerEntity to Player`() {
        val entity = PlayerEntity(
            id = "1",
            name = "Player Name",
            club = "club 1",
            averageSpeed = 45.0,
            averageDistance = 73.0,
            imageUrl = "https://example.com/1.jpg"
        )

        val result = entity.toDomain()

        assertEquals("1", result.id)
        assertEquals("Player Name", result.name)
        assertEquals("club 1", result.club)
        assertEquals(45.0, result.averageSpeed, 0.0)
        assertEquals(73.0, result.averageDistance, 0.0)
        assertEquals("https://example.com/1.jpg", result.imageUrl)
    }
}