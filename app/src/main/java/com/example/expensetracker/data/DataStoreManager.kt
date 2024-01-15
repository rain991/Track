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
import org.koin.java.KoinJavaComponent.inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("UserPreferences")

class DataStoreManager(private val context: Context) {
    companion object {
        private val LOGIN_COUNT = intPreferencesKey("first_launch")
        private val NAME = stringPreferencesKey("user_name")
        private val BUDGET = floatPreferencesKey("user_budget")
        private val CURRENCY = stringPreferencesKey("user_currency")
    }

    suspend fun saveSettings(loginCount: Int, name: String, budget: Float, currency: String) {
        context.dataStore.edit { preferences ->
            preferences[LOGIN_COUNT] = loginCount
            preferences[NAME] = name
            preferences[BUDGET] = budget
            preferences[CURRENCY] = currency
        }
    }

    fun getSettings(): Flow<SettingsData> {
        return context.dataStore.data.map { preferences ->
            SettingsData(
                currency = preferences[CURRENCY] ?: "USD",
                budget = preferences[BUDGET] ?: 0f,
                name = preferences[NAME] ?: "User",
                loginCount = preferences[LOGIN_COUNT] ?: 0
            )
        }
    }
}