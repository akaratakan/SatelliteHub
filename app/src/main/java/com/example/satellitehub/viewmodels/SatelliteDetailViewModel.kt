package com.example.satellitehub.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.satellitehub.base.BaseViewModel
import com.example.usecase.cases.FetchSatelliteDetailUseCase
import com.example.usecase.cases.FetchSatellitePositionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SatelliteDetailViewModel @Inject constructor(
    private val fetchSatelliteDetailUseCase: FetchSatelliteDetailUseCase,
    private val positionsUseCase: FetchSatellitePositionsUseCase
) : BaseViewModel() {

    val detailFlow = fetchSatelliteDetailUseCase.resultFlow
    fun fetchDetail(id:Int) {
        fetchSatelliteDetailUseCase.init(id)
        viewModelScope.launch {
            fetchSatelliteDetailUseCase.launch()
        }
    }

    val positionFlow = positionsUseCase.resultFlow
    fun fetchPositions(id:Int) {
        positionsUseCase.init(id)
        viewModelScope.launch {
            positionsUseCase.launch()
        }
    }

}