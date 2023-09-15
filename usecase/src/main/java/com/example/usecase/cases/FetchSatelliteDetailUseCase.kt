package com.example.usecase.cases

import com.example.model.SateliteDetailItemObject
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

class FetchSatelliteDetailUseCase @Inject constructor(
    private val satelliteRepository: SatelliteRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : FlowUseCase<SateliteDetailItemObject?>() {

    private var id: Int = 0

    fun init(id: Int) {
        this.id = id
    }

    override fun abraKadabra(): Flow<Magic<SateliteDetailItemObject?>> = flow {
        emit(Magic.loading())
        val data = satelliteRepository.fetchSatelliteWithIdFromDB(id)
        val response = if (data != null) {
            satelliteRepository.convertToResponseObject(data)
        } else {
            satelliteRepository.fetchSatelliteDetail()?.first { it.id == id }
        }
        emit(Magic.success(response))
    }.catch {
        Timber.e(it, "Satellite List fetch error")
        emit(Magic.failure(it.message ?: "Something went wrong"))
    }.flowOn(ioDispatcher)
}