package com.example.expensetracker.data.viewmodels


import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.expensetracker.data.DataStoreManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
class ScreenViewModel(private val dataStoreManager: DataStoreManager) : ViewModel() {
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
        _mainScreenAvailable.value=value
    }
}