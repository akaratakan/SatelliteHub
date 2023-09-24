package com.example.satellitehub.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.satellitehub.base.BaseViewModel
import com.example.usecase.cases.FetchSatelliteListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SatelliteListViewModel @Inject constructor(
    private val fetchListUseCase: FetchSatelliteListUseCase
) : BaseViewModel() {

    private val _searchTextState = MutableStateFlow("")
    val searchTextState: StateFlow<String> get() = _searchTextState

    fun setSearchState(query: String) {
        _searchTextState.value = query
    }


    val listFlow = fetchListUseCase.resultFlow
    fun fetchList(name: String) {
        if (name.length > 2 || name.isEmpty()) {
            fetchListUseCase.init(name)
            viewModelScope.launch {
                fetchListUseCase.launch()
            }
        }
    }
}