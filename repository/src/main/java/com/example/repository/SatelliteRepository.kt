package com.example.repository

import com.example.common.util.DataProvider
import com.example.local.dao.SatelliteDao
import com.example.local.entity.SatelliteEntity
import com.example.model.SateliteDetailItemObject
import com.example.model.SatelliteListItemObject
import com.example.model.SatellitePositionListObject
import javax.inject.Inject

class SatelliteRepository @Inject constructor(
    private val dataProvider: DataProvider,
    private val dao: SatelliteDao
) {

    fun insertSatellite(satelliteEntity: SatelliteEntity) {
        return dao.insert(satelliteEntity)
    }

    fun fetchAll(): List<SatelliteEntity>? {
        return dao.fetchAll()
    }

    fun fetchSatelliteWithId(id: Int): SatelliteEntity? {
        return dao.fetchWithId(id)
    }

    fun deleteSatellite(satelliteEntity: SatelliteEntity) {
        return dao.delete(satelliteEntity)
    }

    fun fetchSatelliteList(): Array<SatelliteListItemObject>? {
        return dataProvider.readJSONFromAssets(
            Array<SatelliteListItemObject>::class.java,
            SatelliteObject.SATELLITE_LIST
        )
    }

    fun fetchSatelliteDetail(): Array<SateliteDetailItemObject>? {
        return dataProvider.readJSONFromAssets(
            Array<SateliteDetailItemObject>::class.java,
            SatelliteObject.SATELLITE_DETAIL_LIST
        )
    }

    fun fetchSatellitePositions(): SatellitePositionListObject? {
        return dataProvider.readJSONFromAssets(
            SatellitePositionListObject::class.java,
            SatelliteObject.SATELLITE_POSITION_LIST
        )
    }

    fun convertToEntity(detail:SateliteDetailItemObject): SatelliteEntity {
        return SatelliteEntity(
            id = detail.id,
            costPerLaunch = detail.costPerLaunch,
            firstFlight = detail.firstFlight,
            height = detail.height,
            mass = detail.mass
        )
    }

    fun convertToResponseObject(entity:SatelliteEntity): SateliteDetailItemObject {
        return SateliteDetailItemObject(
            id = entity.id,
            costPerLaunch = entity.costPerLaunch,
            firstFlight = entity.firstFlight,
            height = entity.height,
            mass = entity.mass
        )
    }

}