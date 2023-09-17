package com.example.satellitehub.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.satellitehub.base.BaseViewModel
import com.example.usecase.cases.FetchSatelliteListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SatelliteListViewModel @Inject constructor(
    private val fetchListUseCase: FetchSatelliteListUseCase
) : BaseViewModel() {


    val listFlow = fetchListUseCase.resultFlow
    fun fetchList(name: String) {
        fetchListUseCase.init(name)
        viewModelScope.launch {
            fetchListUseCase.launch()
        }
    }
}