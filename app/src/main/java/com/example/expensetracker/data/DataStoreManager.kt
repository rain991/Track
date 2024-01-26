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
        private val BUDGET = floatPreferencesKey("user_budget")
        private val CURRENCY = stringPreferencesKey("user_currency")
    }

    val loginCountFlow: Flow<Int> = context.dataStore.data.map { preferences -> preferences[LOGIN_COUNT] ?: 0 }
    suspend fun incrementLoginCount() {
        context.dataStore.edit {
            val currentLoginCount = it[LOGIN_COUNT] ?: 0
            it[LOGIN_COUNT] = currentLoginCount + 1
        }
    }

    val nameFlow: Flow<String> = context.dataStore.data.map { preferences -> preferences[NAME] ?: "" }
    suspend fun setName(newName: String) {
        context.dataStore.edit {
            it[NAME] = newName
        }
    }

    val budgetFlow: Flow<Float> = context.dataStore.data.map { preferences -> preferences[BUDGET] ?: 0.0f }
    suspend fun setBudget(newBudget: Float) {
        context.dataStore.edit {
            it[BUDGET] = newBudget
        }
    }

    val CurrencyFlow: Flow<String> = context.dataStore.data.map { preferences -> preferences[CURRENCY] ?: "USD" }
    suspend fun setCurrency(currency: com.example.expensetracker.data.models.Currency) {
        context.dataStore.edit {
            it[CURRENCY] = currency.ticker
        }
    }


}
