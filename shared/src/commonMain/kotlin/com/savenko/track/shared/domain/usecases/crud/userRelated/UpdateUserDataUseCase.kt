package com.savenko.track.shared.domain.usecases.crud.userRelated

import androidx.datastore.preferences.core.Preferences
import com.savenko.track.shared.data.other.dataStore.DataStoreManager

class UpdateUserDataUseCase(private val dataStoreManager: DataStoreManager) {
    suspend operator fun <T> invoke(key: Preferences.Key<T>, value: T?) {
        if (value != null) {
            dataStoreManager.setPreference(key, value)
        }
    }
}