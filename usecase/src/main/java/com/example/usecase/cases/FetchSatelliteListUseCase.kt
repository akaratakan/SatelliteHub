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
) : FlowUseCase<Array<SatelliteListItemObject>?>() {
    override fun abraKadabra(): Flow<Magic<Array<SatelliteListItemObject>?>> = flow{
        emit(Magic.loading())
        val data = satelliteRepository.fetchSatelliteList()
        emit(Magic.success(data))
    }.catch {
        Timber.e(it,"Satellite List fetch error")
        emit(Magic.failure(it.message ?: "Something went wrong"))
    }.flowOn(ioDispatcher)

}