package com.example.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.model.SatelliteDetailItemObject

@Entity(tableName = "satellite_db")
data class SatelliteEntity(
    @PrimaryKey
    val id: Int,
    val costPerLaunch: Int,
    val firstFlight: String,
    val height: Int,
    val mass: Int
) {
    fun toResponse(): SatelliteDetailItemObject {
        return SatelliteDetailItemObject(
            costPerLaunch = costPerLaunch,
            firstFlight = firstFlight,
            height = height,
            id = id,
            mass = mass
        )

    }
}