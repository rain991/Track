package com.savenko.track.shared

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.savenko.track.shared.data.other.dataStore.DataStoreManager
import kotlinx.cinterop.ExperimentalForeignApi
import okio.Path.Companion.toPath
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSApplicationSupportDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

private const val PREFERENCES_NAME = "UserPreferences.preferences_pb"

actual val settingsModule: Module = module {
    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.createWithPath(
            produceFile = { dataStoreFilePath() }
        )
    }
    single<DataStoreManager> { DataStoreManager(get()) }
}

@OptIn(ExperimentalForeignApi::class)
private fun dataStoreFilePath() = (
    NSFileManager.defaultManager.URLForDirectory(
        directory = NSApplicationSupportDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = true,
        error = null
    )?.path ?: error("Unable to resolve Application Support directory for DataStore")
).let { "$it/$PREFERENCES_NAME".toPath() }
