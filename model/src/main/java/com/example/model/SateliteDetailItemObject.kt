package com.example.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SateliteDetailItemObject(
    @Json(name = "cost_per_launch")
    val costPerLaunch: Int,
    @Json(name = "first_flight")
    val firstFlight: String,
    @Json(name = "height")
    val height: Int,
    @Json(name = "id")
    val id: Int,
    @Json(name = "mass")
    val mass: Int
)