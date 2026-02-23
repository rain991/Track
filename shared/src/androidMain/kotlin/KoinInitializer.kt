package com.savenko.track.shared

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

actual class KoinInitializer(private val context: Context) {
    actual fun init() {
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(context)
            modules(
                listOf(
                    settingsModule,
                    appModule,
                    databaseModule,
                    coreModule,
                    domainModule,
                    viewModelModule,
                )
            )
        }
    }
}
