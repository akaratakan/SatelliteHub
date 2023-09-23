package com.example.satellitehub.viewmodels

import com.example.satellitehub.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel() {
    private val _themeFlow = MutableStateFlow(false)
    val themeFlow: StateFlow<Boolean>
        get() = _themeFlow

    fun setTheme(isDark :Boolean) { _themeFlow.value = isDark }
}