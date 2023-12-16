package com.example.expensetracker.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("UserPreferences")

class DataStoreManager(private val context: Context) {
    companion object {
        private val LOGIN_COUNT = intPreferencesKey("first_launch")
        private val NAME = stringPreferencesKey("user_name")
        private val BUDGET = floatPreferencesKey("user_budget")  // could be changed later to INT
        private val CURRENCY = stringPreferencesKey("user_currency")
    }

    suspend fun saveSettings(settingsData: SettingsData) {
        context.dataStore.edit { preferences ->
            preferences[LOGIN_COUNT] = settingsData.getLoginCount()
            preferences[NAME] = settingsData.getName()
            preferences[BUDGET] = settingsData.getBudget()
            preferences[CURRENCY] = settingsData.getCurrency()
        }
    }

    fun getSettings() = context.dataStore.data.map { preferences ->
        return@map SettingsData(
            currency = preferences[CURRENCY] ?: "USD",
            budget = preferences[BUDGET] ?: 0f,
            name = preferences[NAME] ?: "User",
            loginCount = preferences[LOGIN_COUNT] ?: 0
        )
    }
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