package com.example.expensetracker.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


// Data Store is used for settings and value of first_launch_app
private const val USER_PREFERENCES_NAME = "user_preferences"

class UserPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private object Keys {
        val USERNAME = stringPreferencesKey("username")
        val AGE = intPreferencesKey("age")
    }

    val usernameFlow: Flow<String?> = dataStore.data.map { preferences ->
        preferences[Keys.USERNAME]
    }

    val ageFlow: Flow<Int?> = dataStore.data.map { preferences ->
        preferences[Keys.AGE]
    }

    suspend fun saveUsername(username: String) {
        dataStore.edit { preferences ->
            preferences[Keys.USERNAME] = username
        }
    }

    suspend fun saveAge(age: Int) {
        dataStore.edit { preferences ->
            preferences[Keys.AGE] = age
        }
    }

    companion object {
        @Volatile
        private var instance: UserPreferences? = null

        fun getInstance(context: Context): UserPreferences {
            return instance ?: synchronized(this) {
                instance ?: UserPreferences(context.dataStore).also { instance = it }
            }
        }
    }
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_PREFERENCES_NAME)
