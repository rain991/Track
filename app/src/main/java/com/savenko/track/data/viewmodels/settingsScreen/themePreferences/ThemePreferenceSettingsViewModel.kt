package com.savenko.track.data.viewmodels.settingsScreen.themePreferences

import androidx.lifecycle.ViewModel
import com.savenko.track.data.other.dataStore.DataStoreManager
import com.savenko.track.domain.usecases.crud.userRelated.UpdateUserDataUseCase
import com.savenko.track.presentation.themes.Themes

class ThemePreferenceSettingsViewModel(
    private val updateUserDataUseCase: UpdateUserDataUseCase,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    val showPagesNameFlow = dataStoreManager.isShowPageName
    suspend fun setShowPagesNameFlow(value: Boolean) {
        updateUserDataUseCase(newShowPageName = value)
    }

    val useSystemTheme = dataStoreManager.useSystemTheme
    suspend fun setUseSystemTheme(value: Boolean) {
        updateUserDataUseCase(newUseSystemTheme = value)
    }

    val preferableTheme = dataStoreManager.preferableTheme
    suspend fun setPreferableTheme(value: Themes) {
        updateUserDataUseCase(newPreferableTheme = value.name)
    }
}