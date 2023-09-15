package com.example.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SatellitePositionItemObject(
    @Json(name = "id")
    val id: String,
    @Json(name = "positions")
    val positions: List<Position>
)