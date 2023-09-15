package com.example.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Position(
    @Json(name = "posX")
    val posX: Double,
    @Json(name = "posY")
    val posY: Double
)