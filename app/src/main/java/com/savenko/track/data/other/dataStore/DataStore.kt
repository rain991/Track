package com.savenko.track.data.other.dataStore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

const val PREFERENCES_NAME = "UserPreferences"
val Context.dataStore by preferencesDataStore(PREFERENCES_NAME)