package com.example.common.provider

import com.example.common.util.ProviderUtil
import com.example.model.SatelliteDetailItemObject
import com.example.model.SatelliteListItemObject
import com.example.model.SatellitePositionListObject


class SatelliteSource(private val providerUtil: ProviderUtil) : SatelliteInterface {
    override fun fetchSatelliteList(): List<SatelliteListItemObject> {
        val json = providerUtil.readJSONFromAssets(SatelliteObject.SATELLITE_LIST)
        return (providerUtil.parseJson<Array<SatelliteListItemObject>>(json)
            ?: emptyArray()).toList()
    }

    override fun fetchSatelliteDetailList(): List<SatelliteDetailItemObject> {
        val json = providerUtil.readJSONFromAssets(SatelliteObject.SATELLITE_DETAIL_LIST)
        return (providerUtil.parseJson<Array<SatelliteDetailItemObject>>(json)
            ?: emptyArray()).toList()
    }

    override fun fetchSatellitePositionList(): SatellitePositionListObject {
        val json = providerUtil.readJSONFromAssets(SatelliteObject.SATELLITE_POSITION_LIST)
        return providerUtil.parseJson<SatellitePositionListObject>(json)
            ?: SatellitePositionListObject(emptyList())
    }
}