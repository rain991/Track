package com.example.track.data.viewmodels.common

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class TrackScreenManagerViewModel : ViewModel() {
    var pagerState by mutableIntStateOf(1)
}