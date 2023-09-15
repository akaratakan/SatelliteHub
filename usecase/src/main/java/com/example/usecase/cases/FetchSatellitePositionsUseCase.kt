package com.example.usecase.cases

import com.example.model.Position
import com.example.model.SatelliteListItemObject
import com.example.model.SatellitePositionListObject
import com.example.model.generic.Magic
import com.example.repository.SatelliteRepository
import com.example.usecase.di.IoDispatcher
import com.example.usecase.root.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class FetchSatellitePositionsUseCase @Inject constructor(
    private val satelliteRepository: SatelliteRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : FlowUseCase<Position>() {

    private val intervalMillis = 3000L
    private var currentPosition = 0
    private var id: Int = 0

    fun init(id: Int) {
        this.id = id
    }

    override fun abraKadabra(): Flow<Magic<Position>> = flow {
        emit(Magic.loading())
        val data = satelliteRepository.fetchSatellitePositions()
        val positions = data?.list?.first { it.id.toInt() == id }?.positions ?: emptyList()
        while (true) {
            emit(Magic.success(positions[currentPosition]))
            delay(intervalMillis)
            if (currentPosition == 2) {
                currentPosition = 0
            } else {
                currentPosition++
            }
        }
    }.catch {
        Timber.e(it, "Position List fetch error")
        emit(Magic.failure(it.message ?: "Something went wrong"))
    }.flowOn(ioDispatcher)

}