package com.example.common.provider

import com.example.model.SatelliteDetailItemObject
import com.example.model.SatelliteListItemObject
import com.example.model.SatellitePositionListObject

interface SatelliteInterface {
    fun fetchSatelliteList(): List<SatelliteListItemObject>
    fun fetchSatelliteDetailList(): List<SatelliteDetailItemObject>
    fun fetchSatellitePositionList(): SatellitePositionListObject
}