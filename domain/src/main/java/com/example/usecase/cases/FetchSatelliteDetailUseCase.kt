package com.example.usecase.cases

import com.example.model.SatelliteDetailItemObject
import com.example.model.generic.Magic
import com.example.data.repository.SatelliteRepository
import com.example.usecase.di.IoDispatcher
import com.example.usecase.root.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class FetchSatelliteDetailUseCase @Inject constructor(
    private val satelliteRepository: SatelliteRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : FlowUseCase<SatelliteDetailItemObject?>() {

    private var id: Int = 0
    fun init(id: Int) {
        this.id = id
    }

    override fun abraKadabra(): Flow<Magic<SatelliteDetailItemObject?>> = flow {
        emit(Magic.loading())
        val data = satelliteRepository.fetchSatellite(id)
        emit(Magic.success(data))
    }.catch {
        Timber.e(it, "Satellite List fetch error")
        emit(Magic.failure(it.message ?: "Something went wrong"))
    }.flowOn(ioDispatcher)
}