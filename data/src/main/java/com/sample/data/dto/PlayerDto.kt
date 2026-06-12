package com.sample.data.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlayerDto(
    val id: String,
    val name: String,
    val club: String,
    val averageSpeed: Double,
    val averageDistance: Double,
    val avatar: String?
)
