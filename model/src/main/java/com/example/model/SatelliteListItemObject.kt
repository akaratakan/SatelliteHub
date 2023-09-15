package com.example.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SatelliteListItemObject(
    @Json(name = "active")
    val active: Boolean,
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String
)