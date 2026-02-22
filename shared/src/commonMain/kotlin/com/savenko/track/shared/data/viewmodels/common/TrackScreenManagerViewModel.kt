package com.savenko.track.shared.data.viewmodels.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.savenko.track.shared.data.ktor.CurrenciesRatesClient
import com.savenko.track.shared.data.other.dataStore.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Handles current page in pager handled by [TrackScreenManager](com.savenko.track.presentation.screens.core.TrackScreenManager)
 */
class TrackScreenManagerViewModel(
    private val dataStoreManager: DataStoreManager
) :
    ViewModel() {
    private val _pagerState = MutableStateFlow(1)
    val pagerStateValue = _pagerState.asStateFlow()

    private val _loginCountState = MutableStateFlow(-1)
    val loginCountValue = _loginCountState.asStateFlow()

    private val currenciesRatesClient = CurrenciesRatesClient

    init {
        viewModelScope.launch {
            handleLoginCounter()
            currenciesRatesClient.fetchLatestRates()
        }
    }

    fun setPagerState(value: Int) {
        _pagerState.value = value
    }


    private fun handleLoginCounter() {
        var blocker = false
        CoroutineScope(Dispatchers.Default).launch {
            dataStoreManager.loginCountFlow.collect {
                _loginCountState.value = it
                if (it > 0 && !blocker) {
                    dataStoreManager.incrementLoginCount()
                    blocker = true
                }
            }
        }
    }
}
