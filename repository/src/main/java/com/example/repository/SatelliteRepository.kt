package com.example.repository

import com.example.common.util.DataProvider
import com.example.local.dao.SatelliteDao
import com.example.local.entity.SatelliteEntity
import com.example.model.SatelliteDetailItemObject
import com.example.model.SatelliteListItemObject
import com.example.model.SatellitePositionListObject
import timber.log.Timber
import javax.inject.Inject

class SatelliteRepository @Inject constructor(
    private val dataProvider: DataProvider,
    private val dao: SatelliteDao
) {

    fun insertSatellite(detailObject: SatelliteDetailItemObject) {
        val satelliteEntity = convertToEntity(detailObject)
        return try {
            dao.insert(satelliteEntity)
            Timber.d("Satellite insert operation succeed.")
        } catch (e:Exception) {
            Timber.e(e,"Satellite could not insert.")
        }
    }
    fun fetchSatelliteWithIdFromDB(id: Int): SatelliteDetailItemObject? {
        val data = dao.fetchWithId(id)
        data?.let { return convertToResponseObject(data) } ?: return null
    }

    fun fetchSatelliteList(name: String): List<SatelliteListItemObject> {
        val data = dataProvider.readJSONFromAssets(
            Array<SatelliteListItemObject>::class.java,
            SatelliteObject.SATELLITE_LIST
        )?.toList()
        return data?.filter { it.name.contains(other = name, ignoreCase = true) } ?: emptyList()
    }

    fun fetchSatelliteDetail(id:Int): SatelliteDetailItemObject? {
        return dataProvider.readJSONFromAssets(
            Array<SatelliteDetailItemObject>::class.java,
            SatelliteObject.SATELLITE_DETAIL_LIST
        )?.toList()?.first { it.id == id }
    }

    fun fetchSatellitePositions(): SatellitePositionListObject? {
        return dataProvider.readJSONFromAssets(
            SatellitePositionListObject::class.java,
            SatelliteObject.SATELLITE_POSITION_LIST
        )
    }

    private fun convertToEntity(detail: SatelliteDetailItemObject): SatelliteEntity {
        return SatelliteEntity(
            id = detail.id,
            costPerLaunch = detail.costPerLaunch,
            firstFlight = detail.firstFlight,
            height = detail.height,
            mass = detail.mass
        )
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