package com.example.expensetracker.data.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {
    private var _mainScreenAvailable = MutableStateFlow(value = false)
    val mainScreenAvailable = _mainScreenAvailable.asStateFlow()
    fun setMainScreenAvailable(value : Boolean){
        _mainScreenAvailable.update { value }
    }

    private var _isBottomSheetExpanded = MutableStateFlow(value = false)
    val isBottomSheetExpanded = _isBottomSheetExpanded.asStateFlow()


    fun setBottomSheetExpanded(value: Boolean) {
        _isBottomSheetExpanded.update { value }
    }
}