package com.example.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SatellitePositionListObject(
    @Json(name = "list")
    val active: List<SatellitePositionItemObject>
)
