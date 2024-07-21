package com.savenko.track.data.viewmodels.common

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TrackScreenManagerViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val _pagerState = MutableStateFlow(
        savedStateHandle.get<Int>("pagerState") ?: 1
    )
    val pagerStateValue = _pagerState.asStateFlow()

    init {
        savedStateHandle["pagerState"] = _pagerState.value
    }

    fun setPagerState(value: Int) {
        _pagerState.value = value
    }
}
