package com.savenko.track.data.viewmodels.settingsScreen.themePreferences

import androidx.lifecycle.ViewModel
import com.savenko.track.data.other.dataStore.DataStoreManager
import com.savenko.track.domain.usecases.crud.userRelated.UpdateUserDataUseCase
import com.savenko.track.presentation.themes.Themes

/**
 * ThemePreferenceSettingsViewModel can be used in components that could change theme preferences
 */
class ThemePreferenceSettingsViewModel(
    private val updateUserDataUseCase: UpdateUserDataUseCase,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    val showPagesNameFlow = dataStoreManager.isShowPageName
    suspend fun setShowPagesNameFlow(value: Boolean) {
        updateUserDataUseCase(key = DataStoreManager.SHOW_PAGE_NAME, value = value)
    }

    val useSystemTheme = dataStoreManager.useSystemTheme
    suspend fun setUseSystemTheme(value: Boolean) {
        updateUserDataUseCase(key = DataStoreManager.USE_SYSTEM_THEME, value = value)
    }

    val preferableTheme = dataStoreManager.preferableTheme
    suspend fun setPreferableTheme(value: Themes) {
        updateUserDataUseCase(key = DataStoreManager.PREFERABLE_THEME, value = value.name)
    }
}