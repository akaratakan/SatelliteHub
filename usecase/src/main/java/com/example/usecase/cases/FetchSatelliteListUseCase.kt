package com.example.usecase.cases

import com.example.model.SatelliteListItemObject
import com.example.model.generic.Magic
import com.example.repository.SatelliteRepository
import com.example.usecase.di.IoDispatcher
import com.example.usecase.root.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class FetchSatelliteListUseCase @Inject constructor(
    private val satelliteRepository: SatelliteRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : FlowUseCase<List<SatelliteListItemObject>>() {

    lateinit var name:String
    fun init(name:String) {
        this.name = name
    }

    override fun abraKadabra(): Flow<Magic<List<SatelliteListItemObject>>> = flow {
        emit(Magic.loading())
        if (name.length > 2) {
            val data = satelliteRepository.fetchSatelliteList(name)
            emit(Magic.success(data))
        } else if (name.isEmpty()) {
            val data = satelliteRepository.fetchSatelliteList(name)
            emit(Magic.success(data))
        }
    }.catch {
        Timber.e(it, "Satellite List fetch error")
        emit(Magic.failure(it.message ?: "Something went wrong"))
    }.flowOn(ioDispatcher)

}