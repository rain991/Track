package com.example.expensetracker.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("UserPreferences")
class DataStoreManager(private val context: Context) {
    companion object {
        private val LOGIN_COUNT = intPreferencesKey("first_launch")
    }
    suspend fun saveSettings(settingsData: SettingsData) {
        context.dataStore.edit { preferences ->
            preferences[LOGIN_COUNT] = settingsData.loginCount
        }
    }
    fun getSettings() = context.dataStore.data.map{preferences->
        return@map SettingsData(preferences[LOGIN_COUNT] ?: 0) // If any error with DataStore, 0 will be retrieved
    }

//    val getFirstLaunch: Flow<Boolean> = context.dataStore.data.map { preferences ->
//        preferences[FIRST_LAUNCH] ?: true
//    }
//
//    suspend fun saveFirstLaunch(isFirstLaunch: Boolean) {
//        context.dataStore.edit { preferences ->
//            preferences[FIRST_LAUNCH] = isFirstLaunch
//        }
//    }


}
