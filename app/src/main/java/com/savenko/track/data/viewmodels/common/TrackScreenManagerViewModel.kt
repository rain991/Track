package com.savenko.track.data.viewmodels.common

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.savenko.track.data.other.dataStore.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Handles current page in pager handled by [TrackScreenManager](com.savenko.track.presentation.screens.core.TrackScreenManager)
 */
class TrackScreenManagerViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val dataStoreManager: DataStoreManager
) :
    ViewModel() {
    private val _pagerState = MutableStateFlow(
        savedStateHandle.get<Int>("pagerState") ?: 1
    )
    val pagerStateValue = _pagerState.asStateFlow()

    private val _loginCountState = MutableStateFlow(savedStateHandle.get<Int>("loginCount") ?: -1)
    val loginCountValue = _loginCountState.asStateFlow()

    init {
        savedStateHandle["pagerState"] = _pagerState.value
        savedStateHandle["loginCount"] = _loginCountState.value
        viewModelScope.launch {
            handleLoginCounter()
        }
    }

    fun setPagerState(value: Int) {
        _pagerState.value = value
    }


    private fun handleLoginCounter() {
        var blocker = false
        CoroutineScope(Dispatchers.IO).launch {
            dataStoreManager.loginCountFlow.collect {
                _loginCountState.value = it
                savedStateHandle["loginCount"] = _loginCountState.value
                if (it > 0 && !blocker) {
                    dataStoreManager.incrementLoginCount()
                    blocker = true
                }
            }
        }
    }
}
