package com.example.common.provider

import com.example.common.util.ProviderUtil
import com.example.common.util.parseJson
import com.example.model.SatelliteDetailItemObject
import com.example.model.SatelliteListItemObject
import com.example.model.SatellitePositionListObject
import com.squareup.moshi.Moshi


class SatelliteSource(
    private val providerUtil: ProviderUtil,
    private val moshi: Moshi
) : SatelliteInterface {
    override fun fetchSatelliteList(): List<SatelliteListItemObject> {
        val json = providerUtil.readJSONFromAssets(SatelliteObject.SATELLITE_LIST) ?: ""
        return (parseJson<Array<SatelliteListItemObject>>(moshi, json) ?: emptyArray()).toList()
    }

    override fun fetchSatelliteDetailList(): List<SatelliteDetailItemObject> {
        val json = providerUtil.readJSONFromAssets(SatelliteObject.SATELLITE_DETAIL_LIST) ?: ""
        return (parseJson<Array<SatelliteDetailItemObject>>(moshi, json) ?: emptyArray()).toList()
    }

    override fun fetchSatellitePositionList(): SatellitePositionListObject {
        val json = providerUtil.readJSONFromAssets(SatelliteObject.SATELLITE_POSITION_LIST) ?: ""
        return parseJson<SatellitePositionListObject>(moshi, json) ?: SatellitePositionListObject(
            emptyList()
        )
    }
}