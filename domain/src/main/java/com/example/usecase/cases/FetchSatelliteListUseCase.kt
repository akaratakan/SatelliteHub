package com.example.usecase.cases

import com.example.data.repository.SatelliteRepository
import com.example.model.SatelliteListItemObject
import com.example.model.generic.Magic
import com.example.model.generic.mapData
import com.example.usecase.di.IoDispatcher
import com.example.usecase.root.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class FetchSatelliteListUseCase @Inject constructor(
    private val satelliteRepository: SatelliteRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : FlowUseCase<List<SatelliteListItemObject>>() {

    lateinit var query: String
    fun init(query: String) {
        this.query = query
    }

    override fun abraKadabra(): Flow<Magic<List<SatelliteListItemObject>>> = flow {
        emit(Magic.loading())
        val result = satelliteRepository.fetchAllSatellites(query)
        emit(Magic.success(result))
    }.map { response ->
        response.mapData { satellites ->
            satellites.filter {
                it.name.contains(other = query, ignoreCase = true)
            }
        }
    }.catch {
        Timber.e(it, "Satellite List fetch error")
        emit(Magic.failure(it.message ?: "Something went wrong"))
    }.flowOn(ioDispatcher)
}