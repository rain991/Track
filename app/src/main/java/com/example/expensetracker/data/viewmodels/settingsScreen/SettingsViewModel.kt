package com.example.expensetracker.data.viewmodels.settingsScreen

import androidx.lifecycle.ViewModel
import com.example.expensetracker.data.DataStoreManager

class SettingsViewModel(private val dataStoreManager: DataStoreManager) : ViewModel() {
    val showPagesNameFlow = dataStoreManager.isShowPageName
    suspend fun setShowPagesNameFlow(value: Boolean) {
        dataStoreManager.setShowPageName(value)
    }

    val useSystemTheme = dataStoreManager.useSystemTheme
    suspend fun setUseSystemTheme(value : Boolean){
        dataStoreManager.setUseSystemTheme(value)
    }

}