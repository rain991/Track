package com.example.expensetracker.data.viewmodels


import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.expensetracker.data.DataStoreManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
class MainViewModel(private val dataStoreManager: DataStoreManager) : ViewModel() {
    suspend fun initMainScreenAvailable() {
        dataStoreManager.loginCountFlow.collect { loginCount ->
            if (loginCount == 0) {
                setMainScreenAvailable(false)
            } else {
                setMainScreenAvailable(true)
            }
            Log.d("MyLog", "$loginCount")
        }

    }
    private var _mainScreenAvailable = MutableStateFlow(value = false)
    val mainScreenAvailable = _mainScreenAvailable.asStateFlow()
    fun setMainScreenAvailable(value: Boolean) {
        _mainScreenAvailable.update { value }
    }

    private var _isBottomSheetExpanded = MutableStateFlow(value = false)
    val isBottomSheetExpanded = _isBottomSheetExpanded.asStateFlow()


    fun setBottomSheetExpanded(value: Boolean) {
        _isBottomSheetExpanded.update { value }
    }
}