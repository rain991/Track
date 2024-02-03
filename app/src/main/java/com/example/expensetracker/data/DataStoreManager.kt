package com.example.expensetracker.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.expensetracker.data.constants.BUDGET_DEFAULT
import com.example.expensetracker.data.constants.LOGIN_COUNT_DEFAULT
import com.example.expensetracker.data.constants.NAME_DEFAULT
import com.example.expensetracker.data.models.Currency
import com.example.expensetracker.data.models.USD
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val PREFERENCES_NAME = "UserPreferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(PREFERENCES_NAME)
class DataStoreManager(private val context : Context) {


    companion object {  // remember adding new values to constants

        val CURRENCY_DEFAULT: Currency = USD


        private val LOGIN_COUNT = intPreferencesKey("first_launch")
        private val NAME = stringPreferencesKey("user_name")
        private val BUDGET = intPreferencesKey("user_budget")
        private val CURRENCY = stringPreferencesKey("user_currency")
    }


    val loginCountFlow: Flow<Int> = context.dataStore.data.map { preferences -> preferences[LOGIN_COUNT] ?: LOGIN_COUNT_DEFAULT }
    suspend fun incrementLoginCount() {
        context.dataStore.edit {
            val currentLoginCount = it[LOGIN_COUNT] ?: 0
            it[LOGIN_COUNT] = currentLoginCount + 1
        }
    }

    val nameFlow: Flow<String> = context.dataStore.data.map { preferences -> preferences[NAME] ?: NAME_DEFAULT }
    suspend fun setName(newName: String) {
        context.dataStore.edit {
            it[NAME] = newName
        }
    }

    val budgetFlow: Flow<Int> = context.dataStore.data.map { preferences -> preferences[BUDGET] ?: BUDGET_DEFAULT }
    suspend fun setBudget(newBudget: Int) {
        context.dataStore.edit {
            it[BUDGET] = newBudget
        }
    }

    val currencyFlow: Flow<String> = context.dataStore.data.map { preferences -> preferences[CURRENCY] ?: CURRENCY_DEFAULT.ticker }
    suspend fun setCurrency(currency: com.example.expensetracker.data.models.Currency) {
        context.dataStore.edit {
            it[CURRENCY] = currency.ticker
        }
    }
}
