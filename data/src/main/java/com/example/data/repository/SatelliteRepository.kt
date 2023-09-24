package com.example.data.repository

import com.example.local.entity.SatelliteEntity
import com.example.model.SatelliteDetailItemObject
import com.example.model.SatelliteListItemObject
import com.example.model.SatellitePositionItemObject

interface SatelliteRepository {
    fun insertSatellite(satelliteEntity: SatelliteEntity)
    fun fetchAllSatellites(query: String): List<SatelliteListItemObject>
    fun fetchSatellite(id: Int): SatelliteDetailItemObject?
    fun fetchSatellitePositions(id: Int):  List<SatellitePositionItemObject>
}