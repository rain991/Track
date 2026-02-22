package com.savenko.track.shared

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.savenko.track.shared.data.other.dataStore.DataStoreManager
import okio.Path.Companion.toPath
import org.koin.core.module.Module
import org.koin.dsl.module

private const val PREFERENCES_NAME = "UserPreferences.preferences_pb"

actual val settingsModule: Module = module {
    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.createWithPath(
            produceFile = {
                val appContext = get<Context>()
                "${appContext.filesDir.absolutePath}/datastore/$PREFERENCES_NAME".toPath()
            }
        )
    }
    single<DataStoreManager> { DataStoreManager(get()) }
}
