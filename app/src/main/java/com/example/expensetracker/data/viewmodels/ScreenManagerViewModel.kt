package com.example.expensetracker.data.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.DataStoreManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScreenManagerViewModel(private val dataStoreManager: DataStoreManager) : ViewModel() {
    private var _mainScreenAvailable = MutableStateFlow(value = false)
    val mainScreenAvailable = _mainScreenAvailable.asStateFlow()
    init {
        observeLoginCountFlow()
    }

    private fun observeLoginCountFlow() {
        viewModelScope.launch {
            dataStoreManager.loginCountFlow.collect { loginCount ->
                setMainScreenAvailable(loginCount > 0)
            }
        }
    }

    fun setMainScreenAvailable (value : Boolean){  // should be private, but has method in MainActivity
        _mainScreenAvailable.update { value }
    }
}