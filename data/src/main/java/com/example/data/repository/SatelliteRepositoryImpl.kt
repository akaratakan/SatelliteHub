package com.example.data.repository

import com.example.common.provider.SatelliteSource
import com.example.local.dao.SatelliteDao
import com.example.local.entity.SatelliteEntity
import com.example.model.SatelliteDetailItemObject
import com.example.model.SatelliteListItemObject
import com.example.model.SatellitePositionItemObject
import timber.log.Timber
import javax.inject.Inject

class SatelliteRepositoryImpl @Inject constructor(
        private val satelliteSource: SatelliteSource,
        private val dao: SatelliteDao
) : SatelliteRepository {

    override fun insertSatellite(satelliteEntity: SatelliteEntity) {
        dao.insert(satelliteEntity)
    }

    override fun fetchSatellite(id: Int): SatelliteDetailItemObject {
        val responseFromDB = dao.fetchWithId(id)
        return if (responseFromDB != null) {
            Timber.i("Data fetched from room")
            convertToResponseObject(responseFromDB)
        } else {
            Timber.i("Data fetched from provider")
            satelliteSource.fetchSatelliteDetailList().first { it.id == id }
        }
    }

    override fun fetchAllSatellitesFromProvider(query: String): List<SatelliteListItemObject> {
        return satelliteSource.fetchSatelliteList()
    }

    override fun fetchSatellitePositions(id: Int): List<SatellitePositionItemObject> {
        return satelliteSource.fetchSatellitePositionList().list
    }

    private fun convertToResponseObject(entity: SatelliteEntity): SatelliteDetailItemObject {
        return SatelliteDetailItemObject(
                id = entity.id,
                costPerLaunch = entity.costPerLaunch,
                firstFlight = entity.firstFlight,
                height = entity.height,
                mass = entity.mass
        )
    }

}