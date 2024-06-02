package com.example.track.data.viewmodels.settingsScreen

import androidx.lifecycle.ViewModel
import com.example.track.data.other.dataStore.DataStoreManager
import com.example.track.presentation.themes.Themes

class ThemePreferenceSettingsViewModel(private val dataStoreManager: DataStoreManager) : ViewModel() {
    val showPagesNameFlow = dataStoreManager.isShowPageName
    suspend fun setShowPagesNameFlow(value: Boolean) {
        dataStoreManager.setShowPageName(value)
    }

    val useSystemTheme = dataStoreManager.useSystemTheme
    suspend fun setUseSystemTheme(value: Boolean) {
        dataStoreManager.setUseSystemTheme(value)
    }

    val preferableTheme = dataStoreManager.preferableTheme
    suspend fun setPreferableTheme(value: Themes) {
        dataStoreManager.setPreferableTheme(value.name)
    }
}