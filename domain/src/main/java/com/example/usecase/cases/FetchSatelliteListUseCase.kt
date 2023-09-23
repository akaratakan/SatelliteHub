package com.example.usecase.cases

import com.example.data.repository.SatelliteRepository
import com.example.model.SatelliteListItemObject
import com.example.model.generic.Magic
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

    lateinit var query: String
    fun init(query: String) {
        this.query = query
    }

    override fun abraKadabra(): Flow<Magic<List<SatelliteListItemObject>>> = flow {
        emit(Magic.loading())
        val result = satelliteRepository.fetchAllSatellitesFromProvider(query).filter {
            it.name.contains(other = query, ignoreCase = true)
        }
        emit(Magic.success(result))
    }.catch {
        Timber.e(it, "Satellite List fetch error")
        emit(Magic.failure(it.message ?: "Something went wrong"))
    }.flowOn(ioDispatcher)

}