package com.example.track.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.track.data.constants.BUDGET_DEFAULT
import com.example.track.data.constants.LOGIN_COUNT_DEFAULT
import com.example.track.data.constants.NAME_DEFAULT
import com.example.track.data.constants.SHOW_PAGE_NAME_DEFAULT
import com.example.track.data.constants.USE_SYSTEM_THEME_DEFAULT
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext


private const val PREFERENCES_NAME = "UserPreferences"
private val Context.dataStore by preferencesDataStore(PREFERENCES_NAME)

class DataStoreManager(private val context: Context) {
    companion object {
        //USER
        private val LOGIN_COUNT = intPreferencesKey("first_launch")
        private val NAME = stringPreferencesKey("user_name")
        private val BUDGET = intPreferencesKey("user_budget")

        //THEME
        private val USE_SYSTEM_THEME = booleanPreferencesKey("use_system_theme")
        private val PREFERABLE_THEME = stringPreferencesKey("Yellow")
        private val SHOW_PAGE_NAME = booleanPreferencesKey("show_page_name")
    }

    val loginCountFlow: Flow<Int> = context.dataStore.data.map { preferences -> preferences[LOGIN_COUNT] ?: LOGIN_COUNT_DEFAULT }
    suspend fun incrementLoginCount(dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        withContext(dispatcher) {
            context.dataStore.edit {
                val currentLoginCount = it[LOGIN_COUNT] ?: LOGIN_COUNT_DEFAULT
                it[LOGIN_COUNT] = currentLoginCount + 1
            }
        }
    }

    val nameFlow: Flow<String> = context.dataStore.data.map { preferences -> preferences[NAME] ?: NAME_DEFAULT }
    suspend fun setName(newName: String, dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        withContext(dispatcher) {
            context.dataStore.edit {
                it[NAME] = newName
            }
        }
    }

    val budgetFlow: Flow<Int> = context.dataStore.data.map { preferences -> preferences[BUDGET] ?: BUDGET_DEFAULT }
    suspend fun setBudget(newBudget: Int, dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        withContext(dispatcher) {
            context.dataStore.edit {
                it[BUDGET] = newBudget
            }
        }
    }

    val isShowPageName: Flow<Boolean> = context.dataStore.data.map { preferences -> preferences[SHOW_PAGE_NAME] ?: SHOW_PAGE_NAME_DEFAULT }
    suspend fun setShowPageName(value: Boolean, dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        withContext(dispatcher) {
            context.dataStore.edit {
                it[SHOW_PAGE_NAME] = value
            }
        }
    }

    val useSystemTheme: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[USE_SYSTEM_THEME] ?: USE_SYSTEM_THEME_DEFAULT
    }
    suspend fun setUseSystemTheme(value: Boolean, dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        withContext(dispatcher) {
            context.dataStore.edit {
                it[USE_SYSTEM_THEME] = value
            }
        }
    }
}