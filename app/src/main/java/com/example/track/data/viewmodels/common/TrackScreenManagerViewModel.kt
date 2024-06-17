package com.example.track.data.viewmodels.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TrackScreenManagerViewModel : ViewModel() {
    private val _pagerState = MutableStateFlow(1)
    val pagerStateValue = _pagerState.asStateFlow()

    fun setPagerState(value : Int){
        _pagerState.value = value
    }
}