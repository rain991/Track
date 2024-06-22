package com.savenko.track.domain.usecases.userRelated

import com.savenko.track.data.other.dataStore.DataStoreManager

class UpdateUserDataUseCase(private val dataStoreManager: DataStoreManager) {
    suspend operator fun invoke(
        newUserName: String? = null,
        newBudget: Float? = null,
        newUseSystemTheme: Boolean? = null,
        newShowPageName: Boolean? = null,
        newPreferableTheme: String? = null
    ) {
        if (newUserName != null) dataStoreManager.setName(newUserName)
        if (newBudget != null) dataStoreManager.setBudget(newBudget)
        if (newUseSystemTheme != null) dataStoreManager.setUseSystemTheme(newUseSystemTheme)
        if (newShowPageName != null) dataStoreManager.setShowPageName(newShowPageName)
        if (newPreferableTheme != null) dataStoreManager.setPreferableTheme(newPreferableTheme)
    }
}