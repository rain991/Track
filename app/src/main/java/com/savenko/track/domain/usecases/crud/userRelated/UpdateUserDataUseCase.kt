package com.savenko.track.domain.usecases.crud.userRelated

import com.savenko.track.data.other.dataStore.DataStoreManager

class UpdateUserDataUseCase(private val dataStoreManager: DataStoreManager) {
    suspend operator fun invoke(
        newUserName: String? = null,
        newBudget: Float? = null,
        newUseSystemTheme: Boolean? = null,
        newShowPageName: Boolean? = null,
        newPreferableTheme: String? = null
    ) {
        if (newUserName != null) dataStoreManager.setPreference(key = DataStoreManager.NAME, value = newUserName)
        if (newBudget != null) dataStoreManager.setPreference(key = DataStoreManager.BUDGET, value = newBudget)
        if (newUseSystemTheme != null) dataStoreManager.setPreference(key = DataStoreManager.USE_SYSTEM_THEME, value = newUseSystemTheme)
        if (newShowPageName != null) dataStoreManager.setPreference(key = DataStoreManager.SHOW_PAGE_NAME, value = newShowPageName)
        if (newPreferableTheme != null) dataStoreManager.setPreference(key = DataStoreManager.PREFERABLE_THEME, value = newPreferableTheme)
    }
}