package com.savenko.track.data.other.dataStore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.savenko.track.data.other.constants.BUDGET_DEFAULT
import com.savenko.track.data.other.constants.GROUPING_CATEGORY_ID_DEFAULT
import com.savenko.track.data.other.constants.LOGIN_COUNT_DEFAULT
import com.savenko.track.data.other.constants.NAME_DEFAULT
import com.savenko.track.data.other.constants.NON_CATEGORY_FINANCIALS_DEFAULT
import com.savenko.track.data.other.constants.PREFERABLE_THEME_DEFAULT
import com.savenko.track.data.other.constants.SHOW_PAGE_NAME_DEFAULT
import com.savenko.track.data.other.constants.USE_SYSTEM_THEME_DEFAULT
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class DataStoreManager(private val context: Context) {
    companion object {
        //USER
        val LOGIN_COUNT = intPreferencesKey("first_launch")
        val NAME = stringPreferencesKey("user_name")
        val BUDGET = floatPreferencesKey("user_budget")

        //THEME
        val USE_SYSTEM_THEME = booleanPreferencesKey("use_system_theme")
        val PREFERABLE_THEME = stringPreferencesKey("preferable_theme")
        val SHOW_PAGE_NAME = booleanPreferencesKey("show_page_name")

        // Additional settings
        val NON_CATEGORISED_EXPENSES = booleanPreferencesKey("non_category_expenses")
        val GROUPING_EXPENSES_CATEGORY_ID = intPreferencesKey("grouping_expenses_category_id")
        val NON_CATEGORISED_INCOMES = booleanPreferencesKey("non_category_incomes")
        val GROUPING_INCOMES_CATEGORY_ID = intPreferencesKey("grouping_incomes_category_id")
    }
    val loginCountFlow: Flow<Int> =
        context.dataStore.data.map { preferences -> preferences[LOGIN_COUNT] ?: LOGIN_COUNT_DEFAULT }

    suspend fun incrementLoginCount(dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        withContext(dispatcher) {
            context.dataStore.edit {
                val currentLoginCount = it[LOGIN_COUNT] ?: LOGIN_COUNT_DEFAULT
                it[LOGIN_COUNT] = currentLoginCount + 1
            }
        }
    }

    val nameFlow: Flow<String> = context.dataStore.data.map { preferences -> preferences[NAME] ?: NAME_DEFAULT }

    val budgetFlow: Flow<Float> = context.dataStore.data.map { preferences -> preferences[BUDGET] ?: BUDGET_DEFAULT }

    val isShowPageName: Flow<Boolean> =
        context.dataStore.data.map { preferences -> preferences[SHOW_PAGE_NAME] ?: SHOW_PAGE_NAME_DEFAULT }

    val useSystemTheme: Flow<Boolean> =
        context.dataStore.data.map { preferences -> preferences[USE_SYSTEM_THEME] ?: USE_SYSTEM_THEME_DEFAULT }

    val preferableTheme: Flow<String> =
        context.dataStore.data.map { preferences -> preferences[PREFERABLE_THEME] ?: PREFERABLE_THEME_DEFAULT.name }
    val nonCategoryExpenses: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[NON_CATEGORISED_EXPENSES] ?: NON_CATEGORY_FINANCIALS_DEFAULT
    }
    val groupingExpenseCategoryId: Flow<Int> =
        context.dataStore.data.map { preferences ->
            preferences[GROUPING_EXPENSES_CATEGORY_ID] ?: GROUPING_CATEGORY_ID_DEFAULT
        }
    val nonCategoryIncomes: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[NON_CATEGORISED_INCOMES] ?: NON_CATEGORY_FINANCIALS_DEFAULT
    }
    val groupingIncomeCategoryId: Flow<Int> =
        context.dataStore.data.map { preferences ->
            preferences[GROUPING_INCOMES_CATEGORY_ID] ?: GROUPING_CATEGORY_ID_DEFAULT
        }

    suspend fun <T> setPreference(key: Preferences.Key<T>, value: T, dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        withContext(dispatcher) {
            context.dataStore.edit { preferences ->
                preferences[key] = value
            }
        }
    }
}