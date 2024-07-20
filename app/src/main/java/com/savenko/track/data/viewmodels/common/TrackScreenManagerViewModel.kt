package com.savenko.track.data.viewmodels.common

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.savenko.track.data.other.constants.TAG
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TrackScreenManagerViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val _pagerState = MutableStateFlow(
        savedStateHandle.get<Int>("pagerState") ?: 1
    )
    val pagerStateValue = _pagerState.asStateFlow()

    init {
        savedStateHandle["pagerState"] = {
            _pagerState.value
        }
        Log.d(TAG, "TrackScreenManagerViewModel created ")
    }

    fun setPagerState(value: Int) {
        _pagerState.value = value
    }
}
